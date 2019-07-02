package it.polimi.sw2019.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.Cevent.*;
import it.polimi.sw2019.events.client_event.MVevent.NotifyGrabEv;
import it.polimi.sw2019.events.client_event.MVevent.NotifyMoveEv;
import it.polimi.sw2019.events.client_event.MVevent.NotifyReloadEv;
import it.polimi.sw2019.events.server_event.VCevent.GrabEv;
import it.polimi.sw2019.events.server_event.VCevent.MoveEv;
import it.polimi.sw2019.events.server_event.VCevent.ReloadEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.*;
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

import java.io.FileReader;
import java.io.IOException;
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
    private UsePowerUp usePowerUpController;
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
        usePowerUpController = new UsePowerUp();
        shootController = new Shoot(this.players, this.gameboard.getMap());
        killshotController = new Killshot();
        finalFrenzyController = new FinalFrenzy();
        //da rimuovere e chiamare da metodo, il metodo c'è già
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

    public synchronized  void setTimerThreadToTheLogin() {

        try{
            FileReader reader = new FileReader("File_Json/milliSecondsToTimerBeginning.json");
            timerThread.setTime(gson.fromJson( reader , int.class));
            reader.close();
        } catch (IOException ee) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
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

    public synchronized TimerPingThread getTimerPingThread() {
        return timerPingThread;
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

            try ( FileReader reader = new FileReader("File_Json/powerUp.json") ) {

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

            } catch (IOException ee) {
                logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
            }


            //shuffle the deck
            shuffle(this.getGameboard().getPowerUp());
        }
    }

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

    private void createAdditiveWeapons() {

        try ( FileReader reader = new FileReader("File_Json/weaponAdditive.json") ) {

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
                else if (additiveJsons.get(i).getName().equals("Granade Launcher")) {

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

        } catch (IOException ee) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    private void createDoubleAdditiveWeapons() {

        try ( FileReader reader = new FileReader("File_Json/weaponDoubleAdditive.json") ) {

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

        } catch (IOException ee) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    private void createAlternativeWeapons() {

        try ( FileReader reader = new FileReader("File_Json/weaponAlternative.json") ) {
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

        } catch (IOException ee) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    public synchronized void initializeKillShotTrack(String numberOfSkulls) {

        int nOfSkull = 5;
        if (numberOfSkulls.equals("five")) {
            nOfSkull = 5;
        }
        else if (numberOfSkulls.equals("six")) {
            nOfSkull = 6;
        }
        else if (numberOfSkulls.equals("seven")) {
            nOfSkull = 7;
        }
        else {
            nOfSkull = 8;
        }

        for (  int i = 0; i < nOfSkull; i++ ) {

            Skull skull = new Skull();
            this.gameboard.getKillshotTrack().add(skull);
        }

    }

    public synchronized void createAmmo() {

        if ( this.getGameboard().getAmmo().isEmpty() ) {

            createAmmoDoublePowerUp();

            createAmmoTriple();

            //shuffle the deck
            shuffle(this.getGameboard().getAmmo());
        }

    }

    private void createAmmoDoublePowerUp() {

        try ( FileReader reader = new FileReader("File_Json/ammoDoublePowerUp.json") ) {

            List <AmmoDoublePowerUp> ammoDoublePowerUps = new ArrayList<> (18);
            Type REVIEW_TYPE = new TypeToken<List<AmmoDoublePowerUp>>() {}.getType();
            ammoDoublePowerUps = gson.fromJson(reader, REVIEW_TYPE);

            this.getGameboard().getAmmo().addAll(ammoDoublePowerUps);
        } catch( IOException ee ) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    private void createAmmoTriple() {

        try ( FileReader reader = new FileReader("File_Json/ammoTriple.json") ) {

            List <AmmoTriple> ammoTriples = new ArrayList<> (18);
            Type REVIEW_TYPE = new TypeToken<List<AmmoTriple>>() {}.getType();
            ammoTriples = gson.fromJson(reader, REVIEW_TYPE);

            this.getGameboard().getAmmo().addAll(ammoTriples);
        } catch( IOException ee ) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

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

    public Player getTurnOfPlayer () {
        return turnOf.getPlayer();
    }

    public Turn getTurnOf() {
        return this.turnOf;
    }

    public List<ActionEv> getEventActualPlayer() {
        return eventActualPlayer;
    }

    public Player searchPlayer(String nickname) {

        Player playerToReturn = null;

        for(Player p : this.players) {

            if (p.getNickname().equals(nickname)) {
                playerToReturn = p;
            }
        }

        return playerToReturn;
    }

    public void sendStartGame(PlayerRemoteView playerRemoteView) {
        playerRemoteView.sendStartGame();
    }

    public void sendAllModel(PlayerRemoteView playerRemoteView) {
        try{
            List<Player> playerss = new ArrayList<>(5);
            for( Player p : this.players ) {
                playerss.add( (Player) p.clonePlayer() );
            }
            StartGameEv startGameEv = new StartGameEv(  playerss, (Table) this.gameboard.cloneTable());
            playerRemoteView.sendAllModel(startGameEv);
        } catch (CloneNotSupportedException ex) {
            //do nothing
        }

    }



    public void handleEvent(ActionEv actionEv) {

        actionEv.handle(this.executorEventImp, this);
    }

    public void handleEvent(MoveEv moveEv) {

        Player player = searchPlayer( moveEv.getPlayerNickname() );
        this.moveController.handleEvent(moveEv, player);
    }

    public void handleEvent(ReloadEv reloadEv) {

        Player player = searchPlayer( reloadEv.getPlayerNickname() );
        this.reloadController.handleEvent(reloadEv, player);
    }

    public void handleEvent(GrabEv grabEv) {

        Player player = searchPlayer( grabEv.getPlayerNickname() );
        this.grabController.handleEvent(grabEv, player);
    }

/*
    public void handleEvent(PowerupEv powerupEv) {

        Player player = searchPlayer( powerupEv.getPlayerNickname() );
        this.usePowerUpController.handleEvent(powerupEv, player);
    }

    public void handleEvent() {


    }


 */


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

    public void update(WeaponReloadChooseEv weaponReloadChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView( weaponReloadChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent( weaponReloadChooseEv );
        }
    }

    public void update(NotifyEndReloadEv notifyEndReloadEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView( notifyEndReloadEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent( notifyEndReloadEv );
        }
    }

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

    public void update(BarbecueChooseEv barbecueChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(barbecueChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(barbecueChooseEv);
        }
    }

    public void update(BlackHoleChooseEv blackHoleChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(blackHoleChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(blackHoleChooseEv);
        }
    }

    public void update(ChainReactChooseEv chainReactChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(chainReactChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(chainReactChooseEv);
        }
    }

    public void update(CozyFireModeChooseEv cozyFireModeChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(cozyFireModeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(cozyFireModeChooseEv);
        }
    }

    public void update(CyberbladeChooseEv cyberbladeChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(cyberbladeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(cyberbladeChooseEv);
        }
    }

    public void update(ExtraGrenadeChooseEv extraGrenadeChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(extraGrenadeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(extraGrenadeChooseEv);
        }
    }

    public void update(FlamethrowerChooseEv flamethrowerChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(flamethrowerChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(flamethrowerChooseEv);
        }
    }

    public void update(FocusShotChooseEv focusShotChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(focusShotChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(focusShotChooseEv);
        }
    }

    public void update(FurnaceChooseEv furnaceChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(furnaceChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(furnaceChooseEv);
        }
    }

    public void update(GrenadeLaunchChooseEv grenadeLaunchChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(grenadeLaunchChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(grenadeLaunchChooseEv);
        }
    }

    public void update(HeatseekerChooseEv heatseekerChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(heatseekerChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(heatseekerChooseEv);
        }
    }

    public void update(HellionChooseEv hellionChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(hellionChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(hellionChooseEv);
        }
    }

    public void update(HighVoltageChooseEv highVoltageChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(highVoltageChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(highVoltageChooseEv);
        }
    }

    public void update(LockRifleChooseEv lockRifleChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(lockRifleChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(lockRifleChooseEv);
        }
    }

    public void update(LongBarrelChooseEv longBarrelChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(longBarrelChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(longBarrelChooseEv);
        }
    }

    public void update(MachineGunChooseEv machineGunChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(machineGunChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(machineGunChooseEv);
        }
    }

    public void update(NanoTracerChooseEv nanoTracerChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(nanoTracerChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(nanoTracerChooseEv);
        }
    }

    public void update(PhaseGlideChooseEv phaseGlideChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(phaseGlideChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(phaseGlideChooseEv);
        }
    }

    public void update(PiercingModeChooseEv piercingModeChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(piercingModeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(piercingModeChooseEv);
        }
    }

    public void update(PlasmaGunChooseEv plasmaGunChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(plasmaGunChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(plasmaGunChooseEv);
        }
    }

    public void update(PowerChooseEv powerChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(powerChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(powerChooseEv);
        }
    }

    public void update(PowerGloveChooseEv powerGloveChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(powerGloveChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(powerGloveChooseEv);
        }
    }

    public void update(PulvModeChooseEv pulvModeChooseEv) {
        PlayerRemoteView playerRemoteView = searchSpecificPlayerRemoteView(pulvModeChooseEv.getNickname());
        if(playerRemoteView != null) {
            playerRemoteView.sendEvent(pulvModeChooseEv);
        }
    }
}
