package it.polimi.sw2019.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.sw2019.controller.powerup.*;
import it.polimi.sw2019.controller.weaponeffect.*;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.Cevent.*;
import it.polimi.sw2019.events.client_event.MVevent.NotifyGrabEv;
import it.polimi.sw2019.events.client_event.MVevent.NotifyMoveEv;
import it.polimi.sw2019.events.client_event.MVevent.NotifyReloadEv;
import it.polimi.sw2019.events.powerup_events.*;
import it.polimi.sw2019.events.server_event.VCevent.EndEv;
import it.polimi.sw2019.events.server_event.VCevent.GrabEv;
import it.polimi.sw2019.events.server_event.VCevent.MoveEv;
import it.polimi.sw2019.events.server_event.VCevent.ReloadEv;
import it.polimi.sw2019.events.weapon_event.PowerChooseEv;
import it.polimi.sw2019.events.weapon_event.UnpaidEffectEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.*;
import it.polimi.sw2019.exception.FullWeaponDeckException;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.powerup.Newton;
import it.polimi.sw2019.model.powerup.TagbackGrenade;
import it.polimi.sw2019.model.powerup.TargetingScope;
import it.polimi.sw2019.model.powerup.Teleporter;
import it.polimi.sw2019.utility.*;
import it.polimi.sw2019.model.weapon_power.*;
import it.polimi.sw2019.network.Socket.PlayerThread;
import it.polimi.sw2019.view.*;
import it.polimi.sw2019.view.Observer;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Collections.shuffle;

public class Game implements Observer <NotifyReturn> {

    //the model
    private final ArrayList<Player> players;
    private List<PlayerThread> playerThreads;
    private final Table gameboard;
    //the utility
    private TimerThread timerThread;
    private TimerPingThread timerPingThread;
    //for the synchronization
    private Object stop = new Object();
    private Object stopArray = new Object();
    //others controllers to handle event
    private FinalFrenzy finalFrenzyController;
    private Grab grabController;
    private Killshot killshotController;
    private Move moveController;
    private Reload reloadController;
    private Shoot shootController;
    private HashMap<String, EffectController> effectControllers;
    private UsePowerUp usePowerUpController;
    private HashMap<String, PowerUpController> powerUpControllers;
    //the main controller's variables
    private List<ActionEv> eventActualPlayer = new ArrayList<>();
    private Turn turnOf;
    private String gamemode;
    //private State state;
    private String firstPlayer;
    private int mapconfig;
    private boolean out;
    private boolean gameover;
    private boolean gameStarted;
    //for visit pattern
    private ExecutorEventImp executorEventImp = new ExecutorEventImp();
    //to load milliseconds to the timer
    //and to load cards and maps
    private Gson gson = new Gson();
    private static final Logger logger = Logger.getLogger( Game.class.getName() );

    //------ Game's Methods ------

    public Game() {

        players  = new ArrayList<>(5);
        playerThreads = new ArrayList<>(5);
        turnOf = new TurnNormal(null, 0, null);
        gameboard = new Table();
        gamemode = "null";
        //state = new State();
        firstPlayer = "null";
        timerThread = new TimerThread();
        timerPingThread = new TimerPingThread();
        mapconfig = 0;
        out = false;
        gameover = false;
        //controllers
        moveController = new Move(this);
        moveController.addObserver(this);
        reloadController = new Reload(this);
        reloadController.addObserver(this);
        grabController = new Grab(this);
        grabController.addObserver(this);
        usePowerUpController = new UsePowerUp(players, gameboard.getMap());
        powerUpControllers = usePowerUpController.getPowerUpControllers();
        shootController = new Shoot(this.players, this.gameboard.getMap(), this);
        effectControllers = shootController.getWeaponEffectManager().getEffectControllers();
        killshotController = new Killshot();
        finalFrenzyController = new FinalFrenzy();
        setTimerThreadToTheLogin();
    }

    public Table getGameboard() {
        return gameboard;
    }

    /**
     * These method initialize the timer that start after the third player is added to the game waiting for up to two other players to
     * log into the game
     */
    public synchronized  void setTimerThreadToTheLogin() {

        try{
            InputStream in = getClass().getClassLoader().getResourceAsStream("it/polimi/sw2019/file_Json/milliSecondsToTimerBeginning.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            timerThread.setTime(gson.fromJson( reader , int.class));
            reader.close();
        } catch (IOException ee) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    /**
     * These method load the timer that specify the max time that the players have to complete their turn
     */
    public synchronized void setTimerThreadToTheGame() {

        try{
            InputStream in = getClass().getClassLoader().getResourceAsStream("it/polimi/sw2019/file_Json/milliSecondsToTimerTurnOfGame.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
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

    /**
     * @return the list of the players in the game
     */
    public synchronized List<Player> getPlayers() {
        return players;
    }

    public synchronized TimerThread getTimerThread() {
        return timerThread;
    }

    public synchronized TimerPingThread getTimerPingThread() {
        return timerPingThread;
    }

    /**
     * @return true if the match is concluded, false otherwise
     */
    public synchronized boolean getGameover() {
        return gameover;
    }

    /**
     * These method set the status of the match
     * @param status true if the match is finished, false otherwise
     */
    public synchronized void setGameover(boolean status) {
        this.gameover = status;
    }

    public synchronized void setGameStarted(boolean status) {
        this.gameStarted = status;
    }

    public synchronized boolean getGameStarted() {
        return this.gameStarted;
    }

    /**
     * These method add a player to the match
     * @param nickname is the nickname of the player that want to enter the match
     */
    public synchronized void addPlayers(String nickname) {
        Player player = new Player(nickname, 0, null, new PlayerPlance());
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

    public synchronized void askForColor(PlayerRemoteView prv, boolean firstTime, boolean duplicated, List<Player> list) {

        List<String> colorlist = new ArrayList<>(5);
        colorlist.add("blue");  colorlist.add("gray");  colorlist.add("yellow");  colorlist.add("purple");  colorlist.add("green");

        if ( list.isEmpty() ) {

            Color color = new Color(firstTime, duplicated, colorlist);
            prv.requestColor(color);
        }
        else {

            for (Player p : list) {
                Iterator<String> colo = colorlist.iterator();

                while ( colo.hasNext() ) {
                    String es = colo.next();
                    if (p.getCharacter().equals(es)) {
                        colo.remove();
                    }
                }
            }


            Color color = new Color(firstTime, duplicated, colorlist);
            prv.requestColor(color);
        }
    }

    public synchronized void askForMap(PlayerRemoteView prv, boolean firstTime) {
        prv.requestMap(firstTime);
    }

    public synchronized void sendReconnection(boolean firstTime, PlayerRemoteView prv) {
        Reconnection rer = new Reconnection(firstTime, "Reconnection");
        prv.requestNickname(rer);
    }

    public synchronized void askForSkull(PlayerRemoteView prv, boolean firstTime) {
        prv.requestSkull(firstTime);
    }

    public void sendGoodbye(PlayerRemoteView prv) {
        prv.sendGoodbye();
    }

    public void sendOk(PlayerRemoteView prv) {
        prv.sendOk();
    }

    public void sendNotOk(PlayerRemoteView prv) {
        prv.sendNotOk();
    }

    public void sendOut(PlayerRemoteView prv) {
        prv.sendOut();
    }

    public synchronized void sendYouAreFirstPlayer(PlayerRemoteView prv) {
        prv.sendYouAreFirstPlayer();
    }

    public synchronized void sendYouAreNotFirstPlayer(PlayerRemoteView prv) {
        prv.sendYouAreNotFirstPlayer();
    }

    public void sendThereAreSkull(PlayerRemoteView prv) {
        prv.sendThereAreSkull();
    }

    public void sendThereAreNotSkull(PlayerRemoteView prv) {
        prv.sendThereAreNotSkull();
    }

    public void sendThereIsMap(PlayerRemoteView prv) {
        prv.sendThereIsMap();
    }

    public void sendThereIsNotMap(PlayerRemoteView prv) {
        prv.sendThereIsNotMap();
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
    public synchronized void createPowerUp() {

        if ( gameboard.getPowerUp().isEmpty() ) {

            try{
                InputStream in = getClass().getClassLoader().getResourceAsStream("it/polimi/sw2019/file_Json/powerUp.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                List <PowerUpJson> pow = new ArrayList<>(24);
                Type REVIEW_TYPE = new TypeToken<List<PowerUpJson>>() {}.getType();
                pow = gson.fromJson(reader, REVIEW_TYPE);

                for ( int i=0; i<24; i++) {
                    EffectBehaviour eff;
                    if (  pow.get(i).getEffect().equals("TagbackGrenade") ) {
                        eff = new TagbackGrenade();
                    }
                    else if ( pow.get(i).getEffect().equals("Teleporter") ) {
                        eff = new Teleporter();
                    }
                    else if ( pow.get(i).getEffect().equals("Newton") ) {
                        eff = new Newton();
                    }
                    else {
                        eff = new TargetingScope();
                    }

                    PowerUp p = new PowerUp(pow.get(i).getColor(),pow.get(i).getName(), eff);
                    this.gameboard.getPowerUp().add(p);
                }
                reader.close();
            } catch (IOException ee) {
                logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
            }


            //shuffle the deck
            shuffle(this.getGameboard().getPowerUp());
        }
    }

    /**
     * These method initialize the weapon's deck
     */
    public synchronized void createWeapon() {

        if ( this.getGameboard().getWeapon().isEmpty() ) {
            //create the Additive weapons
            createAdditiveWeapons();
            //create the Double Additive weapons
            createDoubleAdditiveWeapons();
            //create the Alternative weapons
            createAlternativeWeapons();

            //shuffle the deck
            shuffle(this.getGameboard().getWeapon() );
        }

    }

    /**
     * These method initialize the weapons with a single optional effect or only the basic effect
     */
    private void createAdditiveWeapons() {

        try{
            InputStream in = getClass().getClassLoader().getResourceAsStream("it/polimi/sw2019/file_Json/weaponAdditive.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            List <AdditiveJson> additiveJsons = new ArrayList<> (5);
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

                    firstEff = new GrenadeLauncher();
                    secondEff = new ExtraGrenade();
                }
                else if (additiveJsons.get(i).getName().equals("Lock Rifle")) {

                    firstEff = new LockRifle();
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

    /**
     * These method initialize the weapons with two optional effects
     */
    private void createDoubleAdditiveWeapons() {

        try{
            InputStream in = getClass().getClassLoader().getResourceAsStream("it/polimi/sw2019/file_Json/weaponDoubleAdditive.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            List <DoubleAdditiveJson> doubleAdditiveJsons = new ArrayList<> (5);
            Type REVIEW_TYPE = new TypeToken<List<DoubleAdditiveJson>>() {}.getType();
            doubleAdditiveJsons = gson.fromJson(reader, REVIEW_TYPE);

            for ( int i = 0; i < 5 ; i++ ) {
                Power firstEff = null;
                Power secondEff = null;
                Power thirdEff = null;

                if (doubleAdditiveJsons.get(i).getName().equals("Cyberblade")) {
                    firstEff = new Cyberblade();
                    secondEff = new Shadowstep();
                    thirdEff = new SliceAndDice();
                }
                else if (doubleAdditiveJsons.get(i).getName().equals("Machine Gun")) {

                    firstEff = new MachineGun();
                    secondEff = new FocusShot();
                    thirdEff = new TurretTripod();
                }
                else if (doubleAdditiveJsons.get(i).getName().equals("Rocket Launcher")) {

                    firstEff = new RocketLauncher();
                    secondEff = new RocketJump();
                    thirdEff = new FragmentingWarhead();
                }
                else if (doubleAdditiveJsons.get(i).getName().equals("Plasma Gun")) {

                    firstEff = new PlasmaGun();
                    secondEff = new PhaseGlide();
                    thirdEff = new ChargedShot();
                }
                else {

                    firstEff = new Thor();
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

    /**
     * These method initialize the weapons with an alternative effect
     */
    private void createAlternativeWeapons() {

        try{
            InputStream in = getClass().getClassLoader().getResourceAsStream("it/polimi/sw2019/file_Json/weaponAlternative.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            List <AlternativeJson> alternativeJsons = new ArrayList<> (11);
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

                    firstEff = new ZX2();
                    secondEff = new ScannerMode();
                }
                else if (alternativeJsons.get(i).getName().equals("Shotgun")) {

                    firstEff = new Shotgun();
                    secondEff = new LongBarrelMode();
                }
                else if (alternativeJsons.get(i).getName().equals("Sledgehammer")) {

                    firstEff = new Sledgehammer();
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

                    firstEff = new Furnace();
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

                    firstEff = new Electroscythe();
                    secondEff = new ReaperMode();
                }
                else {

                    firstEff = new TractorBeam();
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

    /**
     * These method initialize the killShotTrack
     * @param numberOfSkulls the number of skulls for the killShotTrack chosen by the first player
     */
    public synchronized void initializeKillShotTrack(String numberOfSkulls) {

        int nOfSkull;
        switch (numberOfSkulls) {
            case "five":
                nOfSkull = 5;
                break;
            case "six":
                nOfSkull = 6;
                break;
            case "seven":
                nOfSkull = 7;
                break;
            default:
                nOfSkull = 8;
                break;
        }

        for (  int i = 0; i < nOfSkull; i++ ) {

            Skull skull = new Skull();
            this.gameboard.getKillshotTrack().add(skull);
        }

    }

    /**
     * These method initialize the ammo card deck
     */
    public synchronized void createAmmo() {

        if ( this.getGameboard().getAmmo().isEmpty() ) {

            createAmmoDoublePowerUp();

            createAmmoTriple();

            //shuffle the deck
            shuffle(this.getGameboard().getAmmo());
        }

    }

    /**
     * These method initialize the ammo cards with two ammunitions and a powerUp
     */
    private void createAmmoDoublePowerUp() {

        try{
            InputStream in = getClass().getClassLoader().getResourceAsStream("it/polimi/sw2019/file_Json/ammoDoublePowerUp.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            List <AmmoDoublePowerUp> ammoDoublePowerUps = new ArrayList<> (18);
            Type REVIEW_TYPE = new TypeToken<List<AmmoDoublePowerUp>>() {}.getType();
            ammoDoublePowerUps = gson.fromJson(reader, REVIEW_TYPE);

            this.getGameboard().getAmmo().addAll(ammoDoublePowerUps);
            reader.close();
        } catch( IOException ee ) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    /**
     * These method initialize the ammo cards with three ammunitions
     */
    private void createAmmoTriple() {

        try{
            InputStream in = getClass().getClassLoader().getResourceAsStream("it/polimi/sw2019/file_Json/ammoTriple.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            List <AmmoTriple> ammoTriples = new ArrayList<> (18);
            Type REVIEW_TYPE = new TypeToken<List<AmmoTriple>>() {}.getType();
            ammoTriples = gson.fromJson(reader, REVIEW_TYPE);

            this.getGameboard().getAmmo().addAll(ammoTriples);
            reader.close();
        } catch( IOException ee ) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    /**
     * These method initialize the map
     * @param mapNumber the map ID
     *                  zero: the map good for 4 or 5 player
     *                  one: good for any number of players
     *                  two: good for any number of players
     *                  three: good for 3 or 4 players
     */
    public synchronized void createMap(String mapNumber) {

        if( mapNumber.equals("zero") ) {
            createMap1();
        }
        else if ( mapNumber.equals("one") ) {
            createMap2();
        }
        else if ( mapNumber.equals("two") ) {
            createMap3();
        }
        else {
            createMap4();
        }
    }

    /**
     * These method initialize the "zero" map, good for 4 or 5 players
     */
    private void createMap1() {

        List<Space> list1 = new ArrayList<>(3);
        List<Space> list2 = new ArrayList<>(3);
        List<Space> list3 = new ArrayList<>(3);
        List<Space> list4 = new ArrayList<>(3);

        String white = "white";
        String red = "red";
        String yellow = "yellow";
        String blue = "blue";

        Space space1 = new SpaceAmmo(null,null,null,null, white);
        Space space2 = new SpaceGeneration(null,null,null,null, red);
        Space space3 = new SpaceAmmo(null,null,null,null, red);
        Space space4 = new SpaceAmmo(null,null,null,null, white);
        Space space5 = new SpaceAmmo(null,null,null,null,"purple");
        Space space6 = new SpaceAmmo(null,null,null,null,blue);
        Space space7 = new SpaceAmmo(null,null,null,null,yellow);
        Space space8 = new SpaceAmmo(null,null,null,null,yellow);
        Space space9 = new SpaceGeneration(null,null,null,null,blue);
        Space space10 = new SpaceGeneration(null,null,null,null,yellow);
        Space space11 = new SpaceAmmo(null,null,null,null,yellow);
        Space space12 = new SpaceAmmo(null, null, null, null, "green");

        Connection c11 = new Connection(space1,space2,false);               space1.setNorth(c11);
        Connection c12 = new Connection(space1,space4,false);               space1.setEast(c12);
        Connection c13 = new Connection(space1,null,true);      space1.setSouth(c13);
        Connection c14 = new Connection(space1,null,true);      space1.setWest(c14);

        Connection c21 = new Connection(space2, space3, false);             space2.setNorth(c21);
        Connection c22 = new Connection(space2, space5, true);              space2.setEast(c22);
        Connection c23 = new Connection(space2, space1, false);             space2.setSouth(c23);
        Connection c24 = new Connection(space2, null, true);    space2.setWest(c24);

        Connection c31 = new Connection(space3, null,  true);   space3.setNorth(c31);
        Connection c32 = new Connection(space3, space6, false);             space3.setEast(c32);
        Connection c33 = new Connection(space3, space2, false);             space3.setSouth(c33);
        Connection c34 = new Connection(space3, null, true);    space3.setWest(c34);

        list1.add(space1);  list1.add(space2);  list1.add(space3);

        Connection c41 = new Connection(space4, space5,  false);            space4.setNorth(c41);
        Connection c42 = new Connection(space4, space7,  false);            space4.setEast(c42);
        Connection c43 = new Connection(space4, null,  true);   space4.setSouth(c43);
        Connection c44 = new Connection(space4, space1,  false);            space4.setWest(c44);

        Connection c51 = new Connection(space5, space6,  false);            space5.setNorth(c51);
        Connection c52 = new Connection(space5, space8,  true);             space5.setEast(c52);
        Connection c53 = new Connection(space5, space4,  false);            space5.setSouth(c53);
        Connection c54 = new Connection(space5, space2,  true);             space5.setWest(c54);

        Connection c61 = new Connection(space6, null,  true);   space6.setNorth(c61);
        Connection c62 = new Connection(space6, space9,  false);            space6.setEast(c62);
        Connection c63 = new Connection(space6, space5,  false);            space6.setSouth(c63);
        Connection c64 = new Connection(space6, space3,  false);            space6.setWest(c64);

        list2.add(space4);  list2.add(space5);  list2.add(space6);

        Connection c71 = new Connection(space7, space8, false);             space7.setNorth(c71);
        Connection c72 = new Connection(space7, space10, false);            space7.setEast(c72);
        Connection c73 = new Connection(space7, null, true);    space7.setSouth(c73);
        Connection c74 = new Connection(space7, space4, false);             space7.setWest(c74);

        Connection c81 = new Connection(space8, space9, false);             space8.setNorth(c81);
        Connection c82 = new Connection(space8, space11, false);            space8.setEast(c82);
        Connection c83 = new Connection(space8, space7, false);             space8.setSouth(c83);
        Connection c84 = new Connection(space8, space5, true);              space8.setWest(c84);

        Connection c91 = new Connection(space9, null, true);    space9.setNorth(c91);
        Connection c92 = new Connection(space9, space12, false);            space9.setEast(c92);
        Connection c93 = new Connection(space9, space8, false);             space9.setSouth(c93);
        Connection c94 = new Connection(space9, space6, false);             space9.setWest(c94);

        list3.add(space7);  list3.add(space8);  list3.add(space9);

        Connection c101 = new Connection(space10, space11, false);          space10.setNorth(c101);
        Connection c102 = new Connection(space10, null, true);  space10.setEast(c102);
        Connection c103 = new Connection(space10, null, true);  space10.setSouth(c103);
        Connection c104 = new Connection(space10, space7, false);           space10.setWest(c104);

        Connection c111 = new Connection(space11, space12, false);          space11.setNorth(c111);
        Connection c112 = new Connection(space11, null, true);  space11.setEast(c112);
        Connection c113 = new Connection(space11, space10, false);          space11.setSouth(c113);
        Connection c114 = new Connection(space11, space8, false);           space11.setWest(c114);

        Connection c121 = new Connection(space12, null, true);  space12.setNorth(c121);
        Connection c122 = new Connection(space12, null, true);  space12.setEast(c122);
        Connection c123 = new Connection(space12, space11, false);          space12.setSouth(c123);
        Connection c124 = new Connection(space12, space9, false);           space12.setWest(c124);

        list4.add(space10);  list4.add(space11);  list4.add(space12);

        Map map = new Map( list1, list2, list3, list4 );
        this.gameboard.setMap(map);
    }

    /**
     * These method initialize the "one" map: good for any number of players
     */
    private void createMap2() {

        List<Space> spaceList1 = new ArrayList<>(3);
        List<Space> spaceList2 = new ArrayList<>(3);
        List<Space> spaceList3 = new ArrayList<>(3);
        List<Space> spaceList4 = new ArrayList<>(3);

        String white = "white";
        String red = "red";
        String yellow = "yellow";
        String blue = "blue";
        String purple = "purple";

        Space space1 = new SpaceAmmo(null, null, null, null, white);
        Space space2 = new SpaceGeneration(null, null, null, null, red);
        Space space3 = new SpaceAmmo(null, null, null, null, red);
        Space space4 = new SpaceAmmo(null, null, null, null, white);
        Space space5 = new SpaceAmmo(null, null, null, null, purple);
        Space space6 = new SpaceAmmo(null, null, null, null, blue);
        Space space7 = new SpaceAmmo(null, null, null, null, white);
        Space space8 = new SpaceAmmo(null, null, null, null, purple);
        Space space9 = new SpaceGeneration(null, null, null, null, blue);
        Space space10 = new SpaceGeneration(null, null, null, null, yellow);
        Space space11 = new SpaceAmmo(null, null, null, null, yellow);
        //space12 is null

        Connection c11 = new Connection( space1, space2, false);                space1.setNorth(c11);
        Connection c12 = new Connection( space1, space4, false);                space1.setEast(c12);
        Connection c13 = new Connection( space1, null, true);       space1.setSouth(c13);
        Connection c14 = new Connection( space1, null, true);       space1.setWest(c14);

        Connection c21 = new Connection( space2, space3, false);                space2.setNorth(c21);
        Connection c22 = new Connection( space2, space5, true);                 space2.setEast(c22);
        Connection c23 = new Connection( space2, space1, false);                space2.setSouth(c23);
        Connection c24 = new Connection( space2, null, true);       space2.setWest(c24);

        Connection c31 = new Connection( space3, null, true);       space3.setNorth(c31);
        Connection c32 = new Connection( space3, space6, false);                space3.setEast(c32);
        Connection c33 = new Connection( space3, space2, false);                space3.setSouth(c33);
        Connection c34 = new Connection( space3, null, true);       space3.setWest(c34);

        spaceList1.add(space1); spaceList1.add(space2); spaceList1.add(space3);

        Connection c41 = new Connection( space4, space5, false);                space4.setNorth(c41);
        Connection c42 = new Connection( space4, space7, false);                space4.setEast(c42);
        Connection c43 = new Connection( space4, null, true);       space4.setSouth(c43);
        Connection c44 = new Connection( space4, space1, false);                space4.setWest(c44);

        Connection c51 = new Connection( space5, space6, false);                space5.setNorth(c51);
        Connection c52 = new Connection( space5, space8, false);                space5.setEast(c52);
        Connection c53 = new Connection( space5, space4, false);                space5.setSouth(c53);
        Connection c54 = new Connection( space5, space2, true);                 space5.setWest(c54);

        Connection c61 = new Connection( space6, null, true);       space6.setNorth(c61);
        Connection c62 = new Connection( space6, space9, false);                space6.setEast(c62);
        Connection c63 = new Connection( space6, space5, false);                space6.setSouth(c63);
        Connection c64 = new Connection( space6, space3, false);                space6.setWest(c64);

        spaceList2.add(space4); spaceList2.add(space5); spaceList2.add(space6);

        Connection c71 = new Connection( space7, space8, true);                 space7.setNorth(c71);
        Connection c72 = new Connection( space7, space10, false);               space7.setEast(c72);
        Connection c73 = new Connection( space7, null, true);       space7.setSouth(c73);
        Connection c74 = new Connection( space7, space4, false);                space7.setWest(c74);

        Connection c81 = new Connection( space8, space9, false);                space8.setNorth(c81);
        Connection c82 = new Connection( space8, space11, false);               space8.setEast(c82);
        Connection c83 = new Connection( space8, space7, true);                 space8.setSouth(c83);
        Connection c84 = new Connection( space8, space5, false);                space8.setWest(c84);

        Connection c91 = new Connection( space9, null, true);       space9.setNorth(c91);
        Connection c92 = new Connection( space9, null, true);       space9.setEast(c92);
        Connection c93 = new Connection( space9, space8, false);                space9.setSouth(c93);
        Connection c94 = new Connection( space9, space6, false);                space9.setWest(c94);

        spaceList3.add(space7); spaceList3.add(space8); spaceList3.add(space9);

        Connection c101 = new Connection( space10, space11, false);             space10.setNorth(c101);
        Connection c102 = new Connection( space10, null, true);     space10.setEast(c102);
        Connection c103 = new Connection( space10, null, true);     space10.setSouth(c103);
        Connection c104 = new Connection( space10, space7, false);              space10.setWest(c104);

        Connection c111 = new Connection( space11, null, true);     space11.setNorth(c111);
        Connection c112 = new Connection( space11, null, true);     space11.setEast(c112);
        Connection c113 = new Connection( space11, space10, false);             space11.setSouth(c113);
        Connection c114 = new Connection( space11, space8, false);              space11.setWest(c114);

        spaceList4.add(space10); spaceList4.add(space11); spaceList4.add(2,null);

        Map map = new Map( spaceList1, spaceList2, spaceList3, spaceList4 );
        this.gameboard.setMap(map);

    }

    /**
     * These method create the map "two": good for any number of players
     */
    private void createMap3() {

        List<Space> lista = new ArrayList<>(3);
        List<Space> listb = new ArrayList<>(3);
        List<Space> listc = new ArrayList<>(3);
        List<Space> listd = new ArrayList<>(3);

        String blue = "blue";
        String red = "red";
        String white = "white";
        String green = "green";
        String yellow = "yellow";

        //space1 is null
        Space space2 = new SpaceGeneration(null, null, null, null, red);
        Space space3 = new SpaceAmmo(null, null, null, null, blue);
        Space space4 = new SpaceAmmo(null, null, null, null, white);
        Space space5 = new SpaceAmmo(null, null, null, null, red);
        Space space6 = new SpaceAmmo(null, null, null, null, blue);
        Space space7 = new SpaceAmmo(null, null, null, null, yellow);
        Space space8 = new SpaceAmmo(null, null, null, null, yellow);
        Space space9 = new SpaceGeneration(null, null, null, null, blue);
        Space space10 = new SpaceGeneration(null, null, null, null, yellow);
        Space space11 = new SpaceAmmo(null, null, null, null, yellow);
        Space space12 = new SpaceAmmo(null, null, null, null, green);


        Connection c21 = new Connection( space2, space3, false);                space2.setNorth(c21);
        Connection c22 = new Connection( space2, space5, false);                space2.setEast(c22);
        Connection c23 = new Connection( space2, null, true);       space2.setSouth(c23);
        Connection c24 = new Connection( space2, null, true);       space2.setWest(c24);

        Connection c31 = new Connection( space3, null, true);       space3.setNorth(c31);
        Connection c32 = new Connection( space3, space6, false);                space3.setEast(c32);
        Connection c33 = new Connection( space3, space2, false);                space3.setSouth(c33);
        Connection c34 = new Connection( space3, null, true);       space3.setWest(c34);

        lista.add(null);    lista.add(space2);  lista.add(space3);

        Connection c41 = new Connection( space4, space5, false);                space4.setNorth(c41);
        Connection c42 = new Connection( space4, space7, false);                space4.setEast(c42);
        Connection c43 = new Connection( space4, null, true);       space4.setSouth(c43);
        Connection c44 = new Connection( space4, null, true);       space4.setWest(c44);

        Connection c51 = new Connection( space5, space6, true);                 space5.setNorth(c51);
        Connection c52 = new Connection( space5, space8, true);                 space5.setEast(c52);
        Connection c53 = new Connection( space5, space4, false);                space5.setSouth(c53);
        Connection c54 = new Connection( space5, space2, false);                space5.setWest(c54);

        Connection c61 = new Connection( space6, null, true);       space6.setNorth(c61);
        Connection c62 = new Connection( space6, space9, false);                space6.setEast(c62);
        Connection c63 = new Connection( space6, space3, false);                space6.setSouth(c63);
        Connection c64 = new Connection( space6, space5, true);                 space6.setWest(c64);

        listb.add(space4);    listb.add(space5);  listb.add(space6);

        Connection c71 = new Connection( space7, space8, false);                space7.setNorth(c71);
        Connection c72 = new Connection( space7, space10, false);               space7.setEast(c72);
        Connection c73 = new Connection( space7, null, true);       space7.setSouth(c73);
        Connection c74 = new Connection( space7, space4, false);                space7.setWest(c74);

        Connection c81 = new Connection( space8, space9, false);                space8.setNorth(c81);
        Connection c82 = new Connection( space8, space11, false);               space8.setEast(c82);
        Connection c83 = new Connection( space8, space7, false);                space8.setSouth(c83);
        Connection c84 = new Connection( space8, space5, true);                 space8.setWest(c84);

        Connection c91 = new Connection( space9, null, true);       space9.setNorth(c91);
        Connection c92 = new Connection( space9, space12, false);               space9.setEast(c92);
        Connection c93 = new Connection( space9, space8, false);                space9.setSouth(c93);
        Connection c94 = new Connection( space9, space6, false);                space9.setWest(c94);

        listc.add(space7);    listc.add(space8);  listc.add(space9);

        Connection c101 = new Connection( space10, space11, false);             space10.setNorth(c101);
        Connection c102 = new Connection( space10, null, true);     space10.setEast(c102);
        Connection c103 = new Connection( space10, null, true);     space10.setSouth(c103);
        Connection c104 = new Connection( space10, space7, false);              space10.setWest(c104);

        Connection c111 = new Connection( space11, space12, false);             space11.setNorth(c111);
        Connection c112 = new Connection( space11, null, true);     space11.setEast(c112);
        Connection c113 = new Connection( space11, space10, false);             space11.setSouth(c113);
        Connection c114 = new Connection( space11, space8, false);              space11.setWest(c114);

        Connection c121 = new Connection( space12, null, true);     space12.setNorth(c121);
        Connection c122 = new Connection( space12, null, true);     space12.setEast(c122);
        Connection c123 = new Connection( space12, space11, false);             space12.setSouth(c123);
        Connection c124 = new Connection( space12, space9, false);              space12.setWest(c124);

        listd.add(space10);    listd.add(space11);  listd.add(space12);

        Map map = new Map( lista, listb, listc, listd );
        this.gameboard.setMap(map);
    }

    /**
     * These method initialize the "three" map: good for 3 or 4 players
     */
    private void createMap4() {

        List<Space> lista = new ArrayList<>(3);
        List<Space> listb = new ArrayList<>(3);
        List<Space> listc = new ArrayList<>(3);
        List<Space> listd = new ArrayList<>(3);

        String blue = "blue";
        String red = "red";
        String white = "white";
        String yellow = "yellow";

        //space1 is null
        Space space2 = new SpaceGeneration(null, null, null, null, red);
        Space space3 = new SpaceAmmo(null, null, null, null, blue);
        Space space4 = new SpaceAmmo(null, null, null, null, white);
        Space space5 = new SpaceAmmo(null, null, null, null, red);
        Space space6 = new SpaceAmmo(null, null, null, null, blue);
        Space space7 = new SpaceAmmo(null, null, null, null, white);
        Space space8 = new SpaceAmmo(null, null, null, null, red);
        Space space9 = new SpaceGeneration(null, null, null, null, blue);
        Space space10 = new SpaceGeneration(null, null, null, null, yellow);
        Space space11 = new SpaceAmmo(null, null, null, null, yellow);
        //space12 is null


        Connection c21 = new Connection( space2, space3, false);                space2.setNorth(c21);
        Connection c22 = new Connection( space2, space5, false);                space2.setEast(c22);
        Connection c23 = new Connection( space2, null, true);       space2.setSouth(c23);
        Connection c24 = new Connection( space2, null, true);       space2.setWest(c24);

        Connection c31 = new Connection( space3, null, true);       space3.setNorth(c31);
        Connection c32 = new Connection( space3, space6, false);                space3.setEast(c32);
        Connection c33 = new Connection( space3, space2, false);                space3.setSouth(c33);
        Connection c34 = new Connection( space3, null, true);       space3.setWest(c34);

        lista.add(null);    lista.add(space2);  lista.add(space3);

        Connection c41 = new Connection( space4, space5, false);                space4.setNorth(c41);
        Connection c42 = new Connection( space4, space7, false);                space4.setEast(c42);
        Connection c43 = new Connection( space4, null, true);       space4.setSouth(c43);
        Connection c44 = new Connection( space4, null, true);       space4.setWest(c44);

        Connection c51 = new Connection( space5, space6, true);                 space5.setNorth(c51);
        Connection c52 = new Connection( space5, space8, true);                 space5.setEast(c52);
        Connection c53 = new Connection( space5, space4, false);                space5.setSouth(c53);
        Connection c54 = new Connection( space5, space2, false);                space5.setWest(c54);

        Connection c61 = new Connection( space6, null, true);       space6.setNorth(c61);
        Connection c62 = new Connection( space6, space9, false);                space6.setEast(c62);
        Connection c63 = new Connection( space6, space3, false);                space6.setSouth(c63);
        Connection c64 = new Connection( space6, space5, true);                 space6.setWest(c64);

        listb.add(space4);    listb.add(space5);  listb.add(space6);

        Connection c71 = new Connection( space7, space8, true);                 space7.setNorth(c71);
        Connection c72 = new Connection( space7, space10, false);               space7.setEast(c72);
        Connection c73 = new Connection( space7, null, true);       space7.setSouth(c73);
        Connection c74 = new Connection( space7, space4, false);                space7.setWest(c74);

        Connection c81 = new Connection( space8, space9, false);                space8.setNorth(c81);
        Connection c82 = new Connection( space8, space11, false);               space8.setEast(c82);
        Connection c83 = new Connection( space8, space7, true);                 space8.setSouth(c83);
        Connection c84 = new Connection( space8, space5, false);                space8.setWest(c84);

        Connection c91 = new Connection( space9, null, true);       space9.setNorth(c91);
        Connection c92 = new Connection( space9, null, true);       space9.setEast(c92);
        Connection c93 = new Connection( space9, space8, false);                space9.setSouth(c93);
        Connection c94 = new Connection( space9, space6, false);                space9.setWest(c94);

        listc.add(space7); listc.add(space8); listc.add(space9);

        Connection c101 = new Connection( space10, space11, false);             space10.setNorth(c101);
        Connection c102 = new Connection( space10, null, true);     space10.setEast(c102);
        Connection c103 = new Connection( space10, null, true);     space10.setSouth(c103);
        Connection c104 = new Connection( space10, space7, false);              space10.setWest(c104);

        Connection c111 = new Connection( space11, null, true);     space11.setNorth(c111);
        Connection c112 = new Connection( space11, null, true);     space11.setEast(c112);
        Connection c113 = new Connection( space11, space10, false);             space11.setSouth(c113);
        Connection c114 = new Connection( space11, space8, false);              space11.setWest(c114);

        listd.add(space10); listd.add(space11); listd.add(2,null);

        Map map = new Map( lista, listb, listc, listd );
        this.gameboard.setMap(map);

    }

    public int getMapconfig(){
        return mapconfig;
    }

    public void setTurnOfPlayer (Player player) { this.turnOf.setPlayer(player); }

    /**
     * @return the player whose turn it is
     */
    public Player getTurnOfPlayer () {
        return turnOf.getPlayer();
    }

    public Turn getTurnOf() {
        return this.turnOf;
    }

    public List<ActionEv> getEventActualPlayer() {
        return eventActualPlayer;
    }

    /**
     * These method search a player by it's nickname
     * @param nickname the nickname of the searched player
     * @return the player whose nickname is the given nickname
     */
    public Player searchPlayer(String nickname) {

        Player playerToReturn = null;

        for(Player p : this.players) {

            if (p.getNickname().equals(nickname)) {
                playerToReturn = p;
            }
        }

        return playerToReturn;
    }

    /**
     * set the ammo in the correct way,
     * it depends by the number of map
     * @param nrMap the map's number
     */
    public void setAmmo(String nrMap) {

        switch (nrMap) {
            case "zero":
                initializeAmmo0();
                logger.log(Level.INFO, "{Game} initializeAmmo0");
                break;
            case "one":
                initializeAmmo1();
                logger.log(Level.INFO, "{Game} initializeAmmo1");
                break;
            case "two":
                initializeAmmo2();
                logger.log(Level.INFO, "{Game} initializeAmmo2");
                break;
            default:
                initializeAmmo3();
                logger.log(Level.INFO, "{Game} initializeAmmo3");
                break;
        }
    }

    /**
     * set the ammo if the map is the 4x3
     */
    private void initializeAmmo0() {
        try{
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(0,0) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(0,0) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(0,2) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(0,2) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,0) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,0) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,1) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,1) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,2) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,2) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,0) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,0) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,1) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,1) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(3,1) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(3,1) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(3,2) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(3,2) ).takeAmmo().getImageName() );

        } catch (InvalidSpaceException ex) {
            //
        }
    }

    /**
     * These method initialize the ammo cards for the map "one"
     */
    private void initializeAmmo1() {
        try{
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(0,0) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(0,0) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(0,2) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(0,2) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,0) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,0) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,1) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,1) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,2) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,2) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,0) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,0) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,1) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,1) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(3,1) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(3,1) ).takeAmmo().getImageName() );

        } catch (InvalidSpaceException ex) {
            //
        }
    }

    /**
     * These method initialize the ammo cards for the map "two"
     */
    private void initializeAmmo2() {
        try{
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(0,2) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(0,2) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,0) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,0) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,1) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,1) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,2) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,2) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,0) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,0) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,1) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,1) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(3,1) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(3,1) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(3,2) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(3,2) ).takeAmmo().getImageName() );

        } catch (InvalidSpaceException ex) {
            //
        }
    }

    /**
     * These method initialize the ammo cards for the map "three"
     */
    private void initializeAmmo3() {
        try{
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(0,2) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(0,2) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,0) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,0) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,1) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,1) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,2) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(1,2) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,0) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,0) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,1) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(2,1) ).takeAmmo().getImageName() );
            ((SpaceAmmo) this.getGameboard().getMap().getSpace(3,1) ).setAmmo(this.getGameboard().getAmmo().remove(0));
            System.out.println( ((SpaceAmmo) this.getGameboard().getMap().getSpace(3,1) ).takeAmmo().getImageName() );

        } catch (InvalidSpaceException ex) {
            //
        }

    }

    /**
     * set the weapon to the three space generation
     */
    public void setWeapon() {
        //the three space generation are always the same
        try{
            ((SpaceGeneration)this.getGameboard().getMap().getSpace(0,1)).setWeapon(this.getGameboard().getWeapon().remove(0));
            ((SpaceGeneration)this.getGameboard().getMap().getSpace(0,1)).setWeapon(this.getGameboard().getWeapon().remove(0));
            ((SpaceGeneration)this.getGameboard().getMap().getSpace(0,1)).setWeapon(this.getGameboard().getWeapon().remove(0));
            System.out.println( ((SpaceGeneration)this.getGameboard().getMap().getSpace(0,1)).listWeapon().get(0).getName() );
            System.out.println( ((SpaceGeneration)this.getGameboard().getMap().getSpace(0,1)).listWeapon().get(1).getName() );
            System.out.println( ((SpaceGeneration)this.getGameboard().getMap().getSpace(0,1)).listWeapon().get(2).getName() );
            ((SpaceGeneration) this.getGameboard().getMap().getSpace(2,2)).setWeapon(this.getGameboard().getWeapon().remove(0));
            ((SpaceGeneration) this.getGameboard().getMap().getSpace(2,2)).setWeapon(this.getGameboard().getWeapon().remove(0));
            ((SpaceGeneration) this.getGameboard().getMap().getSpace(2,2)).setWeapon(this.getGameboard().getWeapon().remove(0));
            System.out.println( ((SpaceGeneration)this.getGameboard().getMap().getSpace(2,2)).listWeapon().get(0).getName() );
            System.out.println( ((SpaceGeneration)this.getGameboard().getMap().getSpace(2, 2)).listWeapon().get(1).getName() );
            System.out.println( ((SpaceGeneration)this.getGameboard().getMap().getSpace(2, 2)).listWeapon().get(2).getName() );
            ((SpaceGeneration) this.getGameboard().getMap().getSpace(3,0)).setWeapon(this.getGameboard().getWeapon().remove(0));
            ((SpaceGeneration) this.getGameboard().getMap().getSpace(3,0)).setWeapon(this.getGameboard().getWeapon().remove(0));
            ((SpaceGeneration) this.getGameboard().getMap().getSpace(3,0)).setWeapon(this.getGameboard().getWeapon().remove(0));
            System.out.println( ((SpaceGeneration)this.getGameboard().getMap().getSpace(3,0)).listWeapon().get(0).getName() );
            System.out.println( ((SpaceGeneration)this.getGameboard().getMap().getSpace(3,0)).listWeapon().get(1).getName() );
            System.out.println( ((SpaceGeneration)this.getGameboard().getMap().getSpace(3,0)).listWeapon().get(2).getName() );
        } catch (InvalidSpaceException | FullWeaponDeckException ex) {
            //do nothing
        }

    }

    public void sendStartGame(PlayerRemoteView playerRemoteView) {
        playerRemoteView.sendStartGame();
    }

    public void sendAllModel(PlayerRemoteView playerRemoteView) {
     //   try{
            List<Player> playerss = new ArrayList<>();
            for( Player p : this.players ) {
                playerss.add(p);
                //playerss.add( (Player) p.clonePlayer() );
            }
            //StartGameEv startGameEv = new StartGameEv(  playerss, (Table) this.gameboard.cloneTable());
            StartGameEv startGameEv = new StartGameEv(  playerss, this.gameboard);
            playerRemoteView.sendAllModel(startGameEv);
    //    } catch (CloneNotSupportedException ex) {
            //do nothing
     //   }

    }

    public void removePlayerThread(PlayerThread playerThread) {
        if( playerThread != null) {

            for(int i = 0; i < this.playerThreads.size(); i++) {

                if( this.playerThreads.get(i).equals(playerThread)) {
                    this.playerThreads.remove(i);
                }
            }
        }
    }

    public PlayerThread searchPlayerThread(String nickname) {
        PlayerThread ptt = null;
        for(PlayerThread pt : this.playerThreads) {
            if ( pt.getNickname().equals(nickname) ) {
                ptt = pt;
            }
        }
        return ptt;
    }


    public void update(ActionEv actionEv) {

        actionEv.handle(this.executorEventImp, this);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param moveEv the event received from PlayerRemoteView
     */
    public void handleEvent(MoveEv moveEv) {

        Player player = searchPlayer( moveEv.getPlayerNickname() );
        this.moveController.handleEvent(moveEv, player);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param reloadEv the event from PlayerRemoteView
     */
    public void handleEvent(ReloadEv reloadEv) {

        Player player = searchPlayer( reloadEv.getPlayerNickname() );
        this.reloadController.handleEvent(reloadEv, player);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param grabEv the event received from PlayerRemoteView
     */
    public void handleEvent(GrabEv grabEv) {

        Player player = searchPlayer( grabEv.getPlayerNickname() );
        this.grabController.handleEvent(grabEv, player);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param endEv the event received from PlayerRemoteView
     */
    public void handleEvent(EndEv endEv) {
        Player player = searchPlayer( endEv.getPlayerNickname() );
        //
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param barbecueSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(BarbecueSetEv barbecueSetEv){
        String name = BarbecueMode.class.getName().substring(BarbecueMode.class.getName().lastIndexOf('.') + 1);
        ((BarbecueModeCont) effectControllers.get(name)).update(barbecueSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param blackHoleSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(BlackHoleSetEv blackHoleSetEv){
        String name = BlackHole.class.getName().substring(BlackHole.class.getName().lastIndexOf('.') + 1);
        ((BlackHoleCont) effectControllers.get(name)).update(blackHoleSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param chainReactSetEv the event received from PLayerRemoteView
     */
    public void handleEvent(ChainReactSetEv chainReactSetEv){
        String name = ChainReaction.class.getName().substring(ChainReaction.class.getName().lastIndexOf('.') + 1);
        ((ChainReactionCont) effectControllers.get(name)).update(chainReactSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param cozyFireModeSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(CozyFireModeSetEv cozyFireModeSetEv){
        String name = CozyFireMode.class.getName().substring(CozyFireMode.class.getName().lastIndexOf('.') + 1);
        ((CozyFireModeCont) effectControllers.get(name)).update(cozyFireModeSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param cyberbladeSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(CyberbladeSetEv cyberbladeSetEv){
        String name = Cyberblade.class.getName().substring(Cyberblade.class.getName().lastIndexOf('.') + 1);
        ((CyberbladeCont) effectControllers.get(name)).update(cyberbladeSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param extraGrenadeSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(ExtraGrenadeSetEv extraGrenadeSetEv){
        String name = ExtraGrenade.class.getName().substring(ExtraGrenade.class.getName().lastIndexOf('.') + 1);
        ((ExtraGrenadeCont) effectControllers.get(name)).update(extraGrenadeSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param flamethrowerSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(FlamethrowerSetEv flamethrowerSetEv){
        String name = Flamethrower.class.getName().substring(Flamethrower.class.getName().lastIndexOf('.') + 1);
        ((FlamethrowerCont) effectControllers.get(name)).update(flamethrowerSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param focusShotSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(FocusShotSetEv focusShotSetEv){
        String name = FocusShot.class.getName().substring(FocusShot.class.getName().lastIndexOf('.') + 1);
        ((FocusShotCont) effectControllers.get(name)).update(focusShotSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param furnaceSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(FurnaceSetEv furnaceSetEv){
        String name = Furnace.class.getName().substring(Furnace.class.getName().lastIndexOf('.') + 1);
        ((FurnaceCont) effectControllers.get(name)).update(furnaceSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param grenadeLaunchSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(GrenadeLaunchSetEv grenadeLaunchSetEv){
        String name = GrenadeLauncher.class.getName().substring(GrenadeLauncher.class.getName().lastIndexOf('.') + 1);
        ((GrenadeLaunchCont) effectControllers.get(name)).update(grenadeLaunchSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param heatseekerSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(HeatseekerSetEv heatseekerSetEv){
        String name = Heatseeker.class.getName().substring(Heatseeker.class.getName().lastIndexOf('.') + 1);
        ((HeatseekerCont) effectControllers.get(name)).update(heatseekerSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param hellionSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(HellionSetEv hellionSetEv){
        String name = Hellion.class.getName().substring(Hellion.class.getName().lastIndexOf('.') + 1);
        ((HellionCont) effectControllers.get(name)).update(hellionSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param highVoltageSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(HighVoltageSetEv highVoltageSetEv){
        String name = HighVoltage.class.getName().substring(HighVoltage.class.getName().lastIndexOf('.') + 1);
        ((HighVoltageCont) effectControllers.get(name)).update(highVoltageSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param lockRifleSetEv the vent received from PlayerRemoteView
     */
    public void handleEvent(LockRifleSetEv lockRifleSetEv){
        String name = LockRifle.class.getName().substring(LockRifle.class.getName().lastIndexOf('.') + 1);
        ((LockRifleCont) effectControllers.get(name)).update(lockRifleSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param longBarrelSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(LongBarrelSetEv longBarrelSetEv){
        String name = LongBarrelMode.class.getName().substring(LongBarrelMode.class.getName().lastIndexOf('.') + 1);
        ((LongBarrelCont) effectControllers.get(name)).update(longBarrelSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param machineGunSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(MachineGunSetEv machineGunSetEv){
        String name = MachineGun.class.getName().substring(MachineGun.class.getName().lastIndexOf('.') + 1);
        ((MachineGunCont) effectControllers.get(name)).update(machineGunSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param nanoTracerSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(NanoTracerSetEv nanoTracerSetEv){
        String name = NanoTracerMode.class.getName().substring(NanoTracerMode.class.getName().lastIndexOf('.') + 1);
        ((NanoTracerCont) effectControllers.get(name)).update(nanoTracerSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param phaseGlideSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(PhaseGlideSetEv phaseGlideSetEv){
        String name = PhaseGlide.class.getName().substring(PhaseGlide.class.getName().lastIndexOf('.') + 1);
        ((PhaseGlideCont) effectControllers.get(name)).update(phaseGlideSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param piercingModeSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(PiercingModeSetEv piercingModeSetEv){
        String name = PiercingMode.class.getName().substring(PiercingMode.class.getName().lastIndexOf('.') + 1);
        ((PiercingModeCont) effectControllers.get(name)).update(piercingModeSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param plasmaGunSetEv the vent received from PlayerRemoteView
     */
    public void handleEvent(PlasmaGunSetEv plasmaGunSetEv){
        String name = PlasmaGun.class.getName().substring(PlasmaGun.class.getName().lastIndexOf('.') + 1);
        ((PlasmaGunCont) effectControllers.get(name)).update(plasmaGunSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param powerGloveSetEv the event received from PLayerRemoteView
     */
    public void handleEvent(PowerGloveSetEv powerGloveSetEv){
        String name = PowerGlove.class.getName().substring(PowerGlove.class.getName().lastIndexOf('.') + 1);
        ((PowerGloveCont) effectControllers.get(name)).update(powerGloveSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param pulvModeSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(PulvModeSetEv pulvModeSetEv){
        String name = PulverizeMode.class.getName().substring(PulverizeMode.class.getName().lastIndexOf('.') + 1);
        ((PulverizeModeCont) effectControllers.get(name)).update(pulvModeSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param punisherModeSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(PunisherModeSetEv punisherModeSetEv){
        String name = PunisherMode.class.getName().substring(PunisherMode.class.getName().lastIndexOf('.') + 1);
        ((PunisherModeCont) effectControllers.get(name)).update(punisherModeSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param railGunSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(RailGunSetEv railGunSetEv){
        String name = RailGun.class.getName().substring(RailGun.class.getName().lastIndexOf('.') + 1);
        ((RailGunCont) effectControllers.get(name)).update(railGunSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param rocketFistSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(RocketFistSetEv rocketFistSetEv){
        String name = RocketFistMode.class.getName().substring(RocketFistMode.class.getName().lastIndexOf('.') + 1);
        ((RocketFistCont) effectControllers.get(name)).update(rocketFistSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param rocketJumpSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(RocketJumpSetEv rocketJumpSetEv){
        String name = RocketJump.class.getName().substring(RocketJump.class.getName().lastIndexOf('.') + 1);
        ((RocketJumpCont) effectControllers.get(name)).update(rocketJumpSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param rocketLaunchSetEv the event received from PLayerRemoteView
     */
    public void handleEvent(RocketLaunchSetEv rocketLaunchSetEv){
        String name = RocketLauncher.class.getName().substring(RocketLauncher.class.getName().lastIndexOf('.') + 1);
        ((RocketLauncherCont) effectControllers.get(name)).update(rocketLaunchSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param scannerModeSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(ScannerModeSetEv scannerModeSetEv){
        String name = ScannerMode.class.getName().substring(ScannerMode.class.getName().lastIndexOf('.') + 1);
        ((ScannerModeCont) effectControllers.get(name)).update(scannerModeSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param secondLockSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(SecondLockSetEv secondLockSetEv){
        String name = SecondLock.class.getName().substring(SecondLock.class.getName().lastIndexOf('.') + 1);
        ((SecondLockCont) effectControllers.get(name)).update(secondLockSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param shadowstepSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(ShadowstepSetEv shadowstepSetEv){
        String name = Shadowstep.class.getName().substring(Shadowstep.class.getName().lastIndexOf('.') + 1);
        ((ShadowstepCont) effectControllers.get(name)).update(shadowstepSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param shockwaveSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(ShockwaveSetEv shockwaveSetEv){
        String name = Shockwave.class.getName().substring(Shockwave.class.getName().lastIndexOf('.') + 1);
        ((ShockwaveCont) effectControllers.get(name)).update(shockwaveSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param shotgunSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(ShotgunSetEv shotgunSetEv){
        String name = Shotgun.class.getName().substring(Shotgun.class.getName().lastIndexOf('.') + 1);
        ((ShotgunCont) effectControllers.get(name)).update(shotgunSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param sledgehammerSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(SledgehammerSetEv sledgehammerSetEv){
        String name = Sledgehammer.class.getName().substring(Sledgehammer.class.getName().lastIndexOf('.') + 1);
        ((SledgehammerCont) effectControllers.get(name)).update(sledgehammerSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param sliceAndDiceSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(SliceAndDiceSetEv sliceAndDiceSetEv){
        String name = SliceAndDice.class.getName().substring(SliceAndDice.class.getName().lastIndexOf('.') + 1);
        ((SliceAndDiceCont) effectControllers.get(name)).update(sliceAndDiceSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param thorSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(ThorSetEv thorSetEv){
        String name = Thor.class.getName().substring(Thor.class.getName().lastIndexOf('.') + 1);
        ((ThorCont) effectControllers.get(name)).update(thorSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param tractorBeamSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(TractorBeamSetEv tractorBeamSetEv){
        String name = TractorBeam.class.getName().substring(TractorBeam.class.getName().lastIndexOf('.') + 1);
        ((TractorBeamCont) effectControllers.get(name)).update(tractorBeamSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param turretTripodSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(TurretTripodSetEv turretTripodSetEv){
        String name = TurretTripod.class.getName().substring(TurretTripod.class.getName().lastIndexOf('.') + 1);
        ((TurretTripodCont) effectControllers.get(name)).update(turretTripodSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param vortexSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(VortexSetEv vortexSetEv){
        String name = Vortex.class.getName().substring(Vortex.class.getName().lastIndexOf('.') + 1);
        ((VortexCont) effectControllers.get(name)).update(vortexSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param whisperSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(WhisperSetEv whisperSetEv){
        String name = Whisper.class.getName().substring(Whisper.class.getName().lastIndexOf('.') + 1);
        ((WhisperCont) effectControllers.get(name)).update(whisperSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param zx2SetEv the event received from PlayerRemoteView
     */
    public void handleEvent(ZX2SetEv zx2SetEv){
        String name = ZX2.class.getName().substring(ZX2.class.getName().lastIndexOf('.') + 1);
        ((ZX2Cont) effectControllers.get(name)).update(zx2SetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param powerUpSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(PowerUpSetEv powerUpSetEv){
        usePowerUpController.update(powerUpSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param newtonSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(NewtonSetEv newtonSetEv){
        String name = Newton.class.getName().substring(Newton.class.getName().lastIndexOf('.') + 1);
        ((NewtonCont) powerUpControllers.get(name)).update(newtonSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param tagbackGrenadeSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(TagbackGrenadeSetEv tagbackGrenadeSetEv){
        String name = TagbackGrenade.class.getName().substring(TagbackGrenade.class.getName().lastIndexOf('.') + 1);
        ((TagbackGrenadeCont) powerUpControllers.get(name)).update(tagbackGrenadeSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param targetingScopeSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(TargetingScopeSetEv targetingScopeSetEv){
        String name = TargetingScope.class.getName().substring(TargetingScope.class.getName().lastIndexOf('.') + 1);
        ((TargetingScopeCont) powerUpControllers.get(name)).update(targetingScopeSetEv);
    }

    /**
     * These method manage the event received from the PlayerRemoteView
     * @param teleporterSetEv the event received from PlayerRemoteView
     */
    public void handleEvent(TeleporterSetEv teleporterSetEv){
        String name = Teleporter.class.getName().substring(Teleporter.class.getName().lastIndexOf('.') + 1);
        ((TeleporterCont) powerUpControllers.get(name)).update(teleporterSetEv);
    }


    public void update(NotifyReturn notifyReturn) {
        notifyReturn.updateObject(this.executorEventImp, this);
    }

    public String listGamemode(){
        return gamemode;
    }


    private List<PlayerRemoteView> searchAllPlayerRemoteViews() {

        List<PlayerRemoteView> playerRemoteViews = new ArrayList<>(5);

        for(PlayerThread pt : this.playerThreads) {

            playerRemoteViews.add(pt.getPlayerRemoteView());
        }

        return playerRemoteViews;
    }

    private PlayerRemoteView searchSpecificPlayerRemoteView(String nickname) {

        PlayerRemoteView playerRemoteView = null;

        for(PlayerThread pt : this.playerThreads) {

            if ( pt.getNickname().equals(nickname) ) {
                playerRemoteView = pt.getPlayerRemoteView();
            }
        }

        return playerRemoteView;
    }

    public void update(DirectionChooseEv directionChooseEv) {

        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(directionChooseEv.getNickname());

        if(playerRemoteView != null) {
            playerRemoteView.sendEvent( directionChooseEv );
        }


    }

    public void update(NotifyMoveEv notifyMoveEv) {
        List<PlayerRemoteView> playerRemoteViews = searchAllPlayerRemoteViews();
        List<Player> clonePlayers = new ArrayList<>(5);

        try{
            for(Player p : this.players) {
                clonePlayers.add( (Player) p.clonePlayer() );
            }

            notifyMoveEv.setBoardGame( (Table) this.gameboard.cloneTable() );
            notifyMoveEv.setPlayerList(clonePlayers);

            for (PlayerRemoteView playerRemoteView : playerRemoteViews) {

                playerRemoteView.sendEvent(notifyMoveEv);
            }

        } catch (CloneNotSupportedException ex) {
            //do nothing
        }
    }

    public void update(NotifyEndMoveEv notifyEndMoveEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView( notifyEndMoveEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent( notifyEndMoveEv );
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param weaponReloadChooseEv the event to send
     */
    public void update(WeaponReloadChooseEv weaponReloadChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView( weaponReloadChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent( weaponReloadChooseEv );
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param notifyEndReloadEv the event to send
     */
    public void update(NotifyEndReloadEv notifyEndReloadEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView( notifyEndReloadEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent( notifyEndReloadEv );
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param notifyReloadEv the event to send
     */
    public void update(NotifyReloadEv notifyReloadEv) {
        List<PlayerRemoteView> playerRemoteViews = searchAllPlayerRemoteViews();
        List<Player> clonePlayers = new ArrayList<>(5);

        try{
            for(Player p : this.players) {
                clonePlayers.add( (Player) p.clonePlayer() );
            }

            notifyReloadEv.setBoardGame( (Table) this.gameboard.cloneTable() );
            notifyReloadEv.setPlayerList(clonePlayers);

            for (PlayerRemoteView playerRemoteView : playerRemoteViews) {

                playerRemoteView.sendEvent(notifyReloadEv);
            }

        } catch (CloneNotSupportedException ex) {
            //do nothing
        }
    }


    public void update (NotifyGrabEv notifyGrabEv) {

    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param barbecueChooseEv the event to send
     */
    public void update(BarbecueChooseEv barbecueChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(barbecueChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(barbecueChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param blackHoleChooseEv the event to send
     */
    public void update(BlackHoleChooseEv blackHoleChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(blackHoleChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(blackHoleChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param chainReactChooseEv the event to send
     */
    public void update(ChainReactChooseEv chainReactChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(chainReactChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(chainReactChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param cozyFireModeChooseEv the event to send
     */
    public void update(CozyFireModeChooseEv cozyFireModeChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(cozyFireModeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(cozyFireModeChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param cyberbladeChooseEv the event to send
     */
    public void update(CyberbladeChooseEv cyberbladeChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(cyberbladeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(cyberbladeChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param extraGrenadeChooseEv the event to send
     */
    public void update(ExtraGrenadeChooseEv extraGrenadeChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(extraGrenadeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(extraGrenadeChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param flamethrowerChooseEv the event to send
     */
    public void update(FlamethrowerChooseEv flamethrowerChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(flamethrowerChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(flamethrowerChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param focusShotChooseEv the event to send
     */
    public void update(FocusShotChooseEv focusShotChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(focusShotChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(focusShotChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param furnaceChooseEv the event to send
     */
    public void update(FurnaceChooseEv furnaceChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(furnaceChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(furnaceChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param grenadeLaunchChooseEv the event to send
     */
    public void update(GrenadeLaunchChooseEv grenadeLaunchChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(grenadeLaunchChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(grenadeLaunchChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param heatseekerChooseEv the event to send
     */
    public void update(HeatseekerChooseEv heatseekerChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(heatseekerChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(heatseekerChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param hellionChooseEv the event to send
     */
    public void update(HellionChooseEv hellionChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(hellionChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(hellionChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param highVoltageChooseEv the event to send
     */
    public void update(HighVoltageChooseEv highVoltageChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(highVoltageChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(highVoltageChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param lockRifleChooseEv the event to send
     */
    public void update(LockRifleChooseEv lockRifleChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(lockRifleChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(lockRifleChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param longBarrelChooseEv the event to send
     */
    public void update(LongBarrelChooseEv longBarrelChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(longBarrelChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(longBarrelChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param machineGunChooseEv the event to send
     */
    public void update(MachineGunChooseEv machineGunChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(machineGunChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(machineGunChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param nanoTracerChooseEv the event to send
     */
    public void update(NanoTracerChooseEv nanoTracerChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(nanoTracerChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(nanoTracerChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param phaseGlideChooseEv the event to send
     */
    public void update(PhaseGlideChooseEv phaseGlideChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(phaseGlideChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(phaseGlideChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param piercingModeChooseEv the event to send
     */
    public void update(PiercingModeChooseEv piercingModeChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(piercingModeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(piercingModeChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param plasmaGunChooseEv the event to send
     */
    public void update(PlasmaGunChooseEv plasmaGunChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(plasmaGunChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(plasmaGunChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param powerChooseEv the event to send
     */
    public void update(PowerChooseEv powerChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(powerChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(powerChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param powerGloveChooseEv the event to send
     */
    public void update(PowerGloveChooseEv powerGloveChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(powerGloveChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(powerGloveChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param pulvModeChooseEv the event to send
     */
    public void update(PulvModeChooseEv pulvModeChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(pulvModeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(pulvModeChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param punisherModeChooseEv the event to send
     */
    public void update(PunisherModeChooseEv punisherModeChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(punisherModeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(punisherModeChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param railGunChooseEv the event to send
     */
    public void update(RailGunChooseEv railGunChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(railGunChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(railGunChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param rocketFistChooseEv the event to send
     */
    public void update(RocketFistChooseEv rocketFistChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(rocketFistChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(rocketFistChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param rocketJumpChooseEv the event to send
     */
    public void update(RocketJumpChooseEv rocketJumpChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(rocketJumpChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(rocketJumpChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param rocketLaunchChooseEv the event to send
     */
    public void update(RocketLaunchChooseEv rocketLaunchChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(rocketLaunchChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(rocketLaunchChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param scannerModeChooseEv the event to send
     */
    public void update(ScannerModeChooseEv scannerModeChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(scannerModeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(scannerModeChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param secondLockChooseEv the event to send
     */
    public void update(SecondLockChooseEv secondLockChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(secondLockChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(secondLockChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param shadowstepChooseEv the event to send
     */
    public void update(ShadowstepChooseEv shadowstepChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(shadowstepChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(shadowstepChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param shockwaveChooseEv the event to send
     */
    public void update(ShockwaveChooseEv shockwaveChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(shockwaveChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(shockwaveChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param shotgunChooseEv the event to send
     */
    public void update(ShotgunChooseEv shotgunChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(shotgunChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(shotgunChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param sledgehammerChooseEv the event to send
     */
    public void update(SledgehammerChooseEv sledgehammerChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(sledgehammerChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(sledgehammerChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param sliceAndDiceChooseEv the event to send
     */
    public void update(SliceAndDiceChooseEv sliceAndDiceChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(sliceAndDiceChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(sliceAndDiceChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param thorChooseEv the event to send
     */
    public void update(ThorChooseEv thorChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(thorChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(thorChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param tractorBeamChooseEv the event to send
     */
    public void update(TractorBeamChooseEv tractorBeamChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(tractorBeamChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(tractorBeamChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param turretTripodChooseEv the event to send
     */
    public void update(TurretTripodChooseEv turretTripodChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(turretTripodChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(turretTripodChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param vortexChooseEv the event to send
     */
    public void update(VortexChooseEv vortexChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(vortexChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(vortexChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param whisperChooseEv the event to send
     */
    public void update(WhisperChooseEv whisperChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(whisperChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(whisperChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param zx2ChooseEv the event to send
     */
    public void update(ZX2ChooseEv zx2ChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(zx2ChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(zx2ChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param unpaidEffectEv the event to send
     */
    public void update(UnpaidEffectEv unpaidEffectEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(unpaidEffectEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(unpaidEffectEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param powerUpChooseEv the event to send
     */
    public void update(PowerUpChooseEv powerUpChooseEv){
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(powerUpChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(powerUpChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param newtonChooseEv the event to send
     */
    public void update(NewtonChooseEv newtonChooseEv){
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(newtonChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(newtonChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param tagbackGrenadeChooseEv the event to send
     */
    public void update(TagbackGrenadeChooseEv tagbackGrenadeChooseEv){
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(tagbackGrenadeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(tagbackGrenadeChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param targetingScopeChooseEv the event to send
     */
    public void update(TargetingScopeChooseEv targetingScopeChooseEv){
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(targetingScopeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(targetingScopeChooseEv);
        }
    }

    /**
     * These method send the event to the PlayerRemoteView of the current player
     * @param teleporterChooseEv the event to send
     */
    public void update(TeleporterChooseEv teleporterChooseEv){
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(teleporterChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(teleporterChooseEv);
        }
    }

    public List<PlayerThread> getPlayerThreads() {
        return this.playerThreads;
    }
}
