package it.polimi.sw2019.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.sw2019.events.client_event.Cevent.Login;
import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.PowerUpJson;
import it.polimi.sw2019.model.weapon_power.*;
import it.polimi.sw2019.network.Socket.PlayerThread;
import it.polimi.sw2019.utility.TimerThread;
import it.polimi.sw2019.view.ObservableByGame;
import it.polimi.sw2019.view.Observer;
import it.polimi.sw2019.view.PlayerRemoteView;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game implements Observer <ObservableByGame> {

    //the model
    private final ArrayList<Player> players;
    private final Table gameboard;
    //the utility and for the synchronization
    private TimerThread timerThread;
    //for the synchronization
    private Object stop = new Object();
    private Object stopArray = new Object();
    //the main controller's variables
    private final Turn turnOf;
    private String gamemode;
    private State state;
    private String firstPlayer;
    private String[] mapconfig;
    private boolean out;
    private boolean gameover;
    private boolean gameStarted;
    //to load milliseconds to the timer
    //and to load cards and maps
    private Gson gson = new Gson();
    private static final Logger logger = Logger.getLogger( Game.class.getName() );

    //------ Game's Methods ------

    public Game() {

        players  = new ArrayList<>(5);
        turnOf = new TurnNormal(null, 0, null);
        gameboard = new Table();
        gamemode = "null";
        state = new State();
        firstPlayer = "null";
        timerThread = new TimerThread();
        mapconfig = new String[3];
        out = false;
        gameover = false;

        try{
            FileReader reader = new FileReader("File_Json/milliSecondsToTimerBeginning.json");
            timerThread.setTime(gson.fromJson( reader , int.class));
            reader.close();
        } catch (IOException ee) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    public Table getGameboard() {
        return gameboard;
    }

    public synchronized void setTimerThreadToTheGame() {

        try{
            FileReader reader = new FileReader("File_Json/milliSecondsToTimerTurnOfGame.json");
            timerThread.setTurnTime(gson.fromJson( reader , int.class));
            reader.close();

        } catch (IOException ee) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    public void setOut(boolean status) {
        this.out = status;
    }

    public boolean getOut() {
        return this.out;
    }

    public synchronized List<Player> getPlayers() {
        return players;
    }

    public synchronized TimerThread getTimerThread() {
        return timerThread;
    }

    public synchronized boolean getGameover() {
        return gameover;
    }

    public synchronized void setGameover(boolean status) {
        this.gameover = status;
    }

    public synchronized void setGameStarted(boolean status) {
        this.gameStarted = status;
    }

    public synchronized boolean getGameStarted() {
        return this.gameStarted;
    }

    public synchronized void addPlayers(String nickname) {
        Player player = new Player(nickname, 0, null, null);
        players.add(player);
        logger.log(Level.INFO, "{Game} has added a player!\n");
    }

    public synchronized void askForNickname( PlayerRemoteView prv, boolean firstTime, List<Player> list) {

        List<String> namelist = new ArrayList<>(5);

        if( !list.isEmpty() ){

            for( Player y : list ) {
                namelist.add( y.getNickname() );
            }
        }
        Login login = new Login(firstTime, namelist);
        prv.requestNickname(login);

    }

    public synchronized void sendReconnection(boolean firstTime, PlayerRemoteView prv) {
        Reconnection rer = new Reconnection(firstTime, "Reconnection");
        prv.requestNickname(rer);
    }

    public void sendGoodbye(PlayerRemoteView prv) {
        prv.sendGoodbye();
    }

    public void sendOk(PlayerRemoteView prv) {
        prv.sendOk();
    }

    public synchronized Object getStop() {
        return this.stop;
    }

    public synchronized Object getStopArray() {
        return stopArray;
    }

    public void sendPing(PlayerRemoteView prv) {
        prv.sendPing();
    }

    public void setFirstPlayer() {
        this.firstPlayer = this.players.get(0).getNickname();
    }

    public String getFirstPlayer() {
        return this.firstPlayer;
    }

    public synchronized  void removePlayers(PlayerThread pt) {

        Iterator<Player> it = players.iterator();

        while( it.hasNext() ) {

            Player p = it.next();
            if ( p.getNickname().equals(pt.getNickname()) ) {
                it.remove();
                break;
            }
        }
    }

    public void setGameStart() {

        //here how to set the game!
        System.out.println("setting the game!\n");
    }

    /**
     * create the powerupDeck for the gameboard
     */
    public void createPowerUp() {

        if ( gameboard.getPowerUp().isEmpty() ) {

            try {
                List <PowerUpJson> pow = new ArrayList<>(24);
                FileReader reader = new FileReader("File_Json/powerUp.json");
                Type REVIEW_TYPE = new TypeToken<List<PowerUpJson>>() {}.getType();
                pow = gson.fromJson(reader, REVIEW_TYPE);

                for ( int i=0; i<24; i++) {
                    EffectBehaviour eff;
                    if (  pow.get(i).getEffect().equals("Marked") ) {
                        eff = new Marked();
                    }
                    else if ( pow.get(i).getEffect().equals("Teleport") ) {
                        eff = new Teleport();
                    }
                    else if ( pow.get(i).getEffect().equals("MoveEnemy") ) {
                        eff = new MoveEnemy();
                    }
                    else {
                        eff = new ExtraDamage();
                    }

                    PowerUp p = new PowerUp(pow.get(i).getColor(),pow.get(i).getName(), eff);
                    this.gameboard.getPowerUp().add(p);
                }
                reader.close();
            } catch (IOException ee) {
                logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
            }
        }
    }

    public void createWeapon() {

        if ( this.getGameboard().getWeapon().isEmpty() ) {
            //create the Additive weapons
            createAdditiveWeapons();
            //create the Double Additive weapons
            createDoubleAdditiveWeapons();
            //create the Alternative weapons
            createAlternativeWeapons();
        }

    }

    private void createAdditiveWeapons() {

        try {
            List <AdditiveJson> additiveJsons = new ArrayList<> (5);
            FileReader reader = new FileReader("File_Json/weaponAdditive.json");
            Type REVIEW_TYPE = new TypeToken<List<AdditiveJson>>() {}.getType();
            additiveJsons = gson.fromJson(reader, REVIEW_TYPE);

            for ( int i = 0; i < 5 ; i++ ) {
                Power firstEff = null;
                Power secondEff = null;

                if (additiveJsons.get(i).getName().equals("Vortex Cannon")) {
                    firstEff = new Vortex();
                    secondEff = new BlackHole();
                }
                else if (additiveJsons.get(i).getName().equals("Grenade Launcher")) {

                    firstEff = new DamageMove();
                    secondEff = new ExtraGrenade();
                }
                else if (additiveJsons.get(i).getName().equals("Lock Rifle")) {

                    firstEff = new TwoDamageMark();
                    secondEff = new SecondLock();
                }
                else if (additiveJsons.get(i).getName().equals("Heatseeker")) {

                    firstEff = new Heatseeker();
                    secondEff = null;
                }
                else {

                    firstEff = new Whisper();
                    secondEff = null;
                }

                Additive additive = new Additive( additiveJsons.get(i).getName(), firstEff, additiveJsons.get(i).getDescriptionPower(),
                        secondEff, additiveJsons.get(i).getAdditiveCost() , additiveJsons.get(i).getDescriptionAdditivePower(),
                        additiveJsons.get(i).getRechargeCost() );

                this.gameboard.getWeapon().add(additive);
            }
            reader.close();
        } catch (IOException ee) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    private void createDoubleAdditiveWeapons() {

        try {
            List <DoubleAdditiveJson> doubleAdditiveJsons = new ArrayList<> (5);
            FileReader reader = new FileReader("File_Json/weaponDoubleAdditive.json");
            Type REVIEW_TYPE = new TypeToken<List<DoubleAdditiveJson>>() {}.getType();
            doubleAdditiveJsons = gson.fromJson(reader, REVIEW_TYPE);

            for ( int i = 0; i < 5 ; i++ ) {
                Power firstEff = null;
                Power secondEff = null;
                Power thirdEff = null;

                if (doubleAdditiveJsons.get(i).getName().equals("Cyberblade")) {
                    firstEff = new TwoDamageSameSpace();
                    secondEff = new Shadowstep();
                    thirdEff = new SliceAndDice();
                }
                else if (doubleAdditiveJsons.get(i).getName().equals("Machine Gun")) {

                    firstEff = new TwoTargetDamage();
                    secondEff = new FocusShot();
                    thirdEff = new TurretTripod();
                }
                else if (doubleAdditiveJsons.get(i).getName().equals("Rocket Launcher")) {

                    firstEff = new RocketLauncher();
                    secondEff = new RocketJump();
                    thirdEff = new FragmentingWarhead();
                }
                else if (doubleAdditiveJsons.get(i).getName().equals("Plasma Gun")) {

                    firstEff = new TwoDamage();
                    secondEff = new PhaseGlide();
                    thirdEff = new ChargedShot();
                }
                else {

                    firstEff = new TwoDamage();
                    secondEff = new ChainReaction();
                    thirdEff = new HighVoltage();
                }

                DoubleAdditive doubleAdditive = new DoubleAdditive( doubleAdditiveJsons.get(i).getName(), firstEff, doubleAdditiveJsons.get(i).getDescriptionPower(),
                        secondEff, doubleAdditiveJsons.get(i).getFirstExtraCost(), thirdEff, doubleAdditiveJsons.get(i).getSecondExtraCost(),
                        doubleAdditiveJsons.get(i).getDescriptionFirstAdditivePower(), doubleAdditiveJsons.get(i).getDescriptionSecondAdditivePower(),
                        doubleAdditiveJsons.get(i).getRechargeCost());

                this.gameboard.getWeapon().add(doubleAdditive);
            }
            reader.close();
        } catch (IOException ee) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    private void createAlternativeWeapons() {

        try {
            List <AlternativeJson> alternativeJsons = new ArrayList<> (11);
            FileReader reader = new FileReader("File_Json/weaponAlternative.json");
            Type REVIEW_TYPE = new TypeToken<List<AlternativeJson>>() {}.getType();
            alternativeJsons = gson.fromJson(reader, REVIEW_TYPE);

            for ( int i = 0; i < 11 ; i++ ) {
                Power firstEff = null;
                Power secondEff = null;

                if (alternativeJsons.get(i).getName().equals("Shockwave")) {
                    firstEff = new Shockwave();
                    secondEff = new TsunamiMode();
                }
                else if (alternativeJsons.get(i).getName().equals("Zx-2")) {

                    firstEff = new DamageTwoMark();
                    secondEff = new ScannerMode();
                }
                else if (alternativeJsons.get(i).getName().equals("Shotgun")) {

                    firstEff = new ThreeDamageMove();
                    secondEff = new LongBarrelMode();
                }
                else if (alternativeJsons.get(i).getName().equals("Sledgehammer")) {

                    firstEff = new TwoDamageSameSpace();
                    secondEff = new PulverizeMode();
                }
                else if (alternativeJsons.get(i).getName().equals("Rail Gun")) {

                    firstEff = new RailGun();
                    secondEff = new PiercingMode();
                }
                else if (alternativeJsons.get(i).getName().equals("Power Glove")) {

                    firstEff = new PowerGlove();
                    secondEff = new RocketFistMode();
                }
                else if (alternativeJsons.get(i).getName().equals("Furnace")) {

                    firstEff = new RoomDamage();
                    secondEff = new CozyFireMode();
                }
                else if (alternativeJsons.get(i).getName().equals("Hellion")) {

                    firstEff = new Hellion();
                    secondEff = new NanoTracerMode();
                }
                else if (alternativeJsons.get(i).getName().equals("Flamethrower")) {

                    firstEff = new Flamethrower();
                    secondEff = new BarbecueMode();
                }
                else if (alternativeJsons.get(i).getName().equals("Electroscythe")) {

                    firstEff = new DamageAllSameSpace();
                    secondEff = new ReaperMode();
                }
                else {

                    firstEff = new MoveDamage();
                    secondEff = new PunisherMode();
                }

                Alternative alternative = new Alternative( alternativeJsons.get(i).getName(), firstEff,
                        alternativeJsons.get(i).getDescriptionPower(), secondEff, alternativeJsons.get(i).getExtraCost(),
                        alternativeJsons.get(i).getDescriptionAlternativePower(), alternativeJsons.get(i).getRechargeCost());

                this.gameboard.getWeapon().add(alternative);
            }
            reader.close();
        } catch (IOException ee) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    public void initializeKillShotTrack(int numberOfSkulls) {

        for (  int i = 0; i < numberOfSkulls; i++) {

            Skull skull = new Skull();
            this.getGameboard().getKillshotTrack().add(skull);
        }

    }

    public void createAmmo() {

        if ( this.getGameboard().getAmmo().isEmpty() ) {

            createAmmoDoublePowerUp();

            createAmmoTriple();
        }

    }

    public void createAmmoDoublePowerUp() {

        try{
            List <AmmoDoublePowerUp> ammoDoublePowerUps = new ArrayList<> (18);
            FileReader reader = new FileReader("File_Json/ammoDoublePowerUp.json");
            Type REVIEW_TYPE = new TypeToken<List<AmmoDoublePowerUp>>() {}.getType();
            ammoDoublePowerUps = gson.fromJson(reader, REVIEW_TYPE);

            this.getGameboard().getAmmo().addAll(ammoDoublePowerUps);
            reader.close();
        } catch( IOException ee ) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    public void createAmmoTriple() {

        try{
            List <AmmoTriple> ammoTriples = new ArrayList<> (18);
            FileReader reader = new FileReader("File_Json/ammoTriple.json");
            Type REVIEW_TYPE = new TypeToken<List<AmmoTriple>>() {}.getType();
            ammoTriples = gson.fromJson(reader, REVIEW_TYPE);

            this.getGameboard().getAmmo().addAll(ammoTriples);
            reader.close();
        } catch( IOException ee ) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    //.............................FROM HERE OLD THINGS........................

    public void update (ObservableByGame message) {

        if ( message instanceof Ammo ) {

            //i have to pass the object to the remoteView

        }
    }


    public void selectGameboard(String type ){

    }

    public void setGamemode(String type){

    }

    public String[] listGameboard(){
        return mapconfig;
    }

    public String listGamemode(){
        return gamemode;
    }

    public State getState(){
        return state;
    }

    private void loadMap(int maptype){

    }

    private void initializeWeaponDeck(){

    }

    private void initializeAmmoDeck(){

    }

    private void initializeTable(){

    }


  //  public void setTurnOfPlayer (Player player) { this.turnOf.setPlayer(player); }

    public Player getTurnOfPlayer () {
        return turnOf.getPlayer();
    }

}
