package it.polimi.sw2019.network.Socket;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.controller.MultiGame;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.exception.CancellPlayerException;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.utility.TimerThread;
import it.polimi.sw2019.view.PlayerRemoteView;
import it.polimi.sw2019.view.PowerUpRemoteView;
import it.polimi.sw2019.view.TableRemoteView;
import it.polimi.sw2019.view.WeaponRemoteView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PlayerThread implements Runnable {

    //client connection
    private Socket connection;
    //input from client
    private Scanner input;
    //output to client
    private PrintWriter output;
    //if the thread is suspended
    private boolean suspended;
    //the player's nickname
    private String nickname = "pippo";
    //
    private String nameOfMatch;
    //to contain what the player write ( his actions )
    private String string;
    //the controller
    private MultiGame multiGameController;
    private Game gameController;

    //the logger
    private static final Logger logger = Logger.getLogger( PlayerThread.class.getName() );

    //the game's Remote View
    private PlayerRemoteView playerRemoteView;
    private TableRemoteView tableRemoteView;
    private WeaponRemoteView weaponRemoteView;
    private PowerUpRemoteView powerUpRemoteView;

    //anzichè il Game, devo passargli il multigame dal socket
    public PlayerThread(Socket socket, Game controller){
        //set the player's number and the socket
        connection = socket;
        suspended = true;
        string = "bho";
        //gameLocker = new ReentrantLock();
        //turnOfOtherPlayers = gameLocker.newCondition();
        //otherPlayerConnected = gameLocker.newCondition();
        gameController = controller;
        playerRemoteView = new PlayerRemoteView(socket);
        tableRemoteView = new TableRemoteView(socket);
        weaponRemoteView = new WeaponRemoteView(socket);
        powerUpRemoteView = new PowerUpRemoteView(socket);

        try{
            //what the client is writing
            input = new Scanner( connection.getInputStream() );
            //what the server is sending to client
            output = new PrintWriter( connection.getOutputStream() );

        }catch(IOException e){

            logger.log( Level.SEVERE, e.toString(), e);
            System.exit(1);
        }

    }//END of the CONSTRUCTOR

    /**
     * this call the method run of interface runnable
     * in this method is focused the wait for all the connections and
     * the main game's loop
     */
    public void run() {
        // the game is already started
        if ( gameController.getGameStarted() ) {

            //i have to define how reconnect the player
            boolean first = true;
            boolean out = false;

            while (!out) {

                gameController.sendReconnection(first, playerRemoteView);
                string = this.playerRemoteView.waitForNickname();

                if (string.equals("quit")) {
                    try {
                        //the client connection is closed
                        logger.log(Level.FINE, " {ServerSocket} The connection with a player is closed!\n");
                        this.connection.close();
                        this.input.close();
                        this.output.close();

                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                } else {

                    boolean find = false;
                    for (Player io : gameController.getPlayers()) {
                        if (io.getNickname().equals(string)) {
                            //there is the player
                            find = true;
                            out = true;
                        }
                    }
                    if (!find) {
                        gameController.sendGoodbye(playerRemoteView);
                        try {
                            //the client connection is closed
                            logger.log(Level.FINE, " {ServerSocket} The connection with player is closed!\n");
                            this.connection.close();
                            this.input.close();
                            this.output.close();

                        } catch (IOException e) {

                            e.printStackTrace();
                            System.exit(1);
                        }
                    }
                }

                this.nickname = string;
            }

            //if he reconnect himself
            startGame();
        }
        else if ( gameController.getGameover() ) {
            gameController.sendGoodbye(playerRemoteView);

            try{
                //the client connection is closed
                logger.log(Level.FINE, " {ServerSocket} The connection with a player is closed!\n");
                this.connection.close();
                this.input.close();
                this.output.close();
                gameController.removePlayers(this);

            }catch(IOException e) {

                e.printStackTrace();
                System.exit(1);
            }
        }
        else {


            //you are connected with the game
            try {

                //this.playerRemoteView.addObserver(this.gameController);
                //this.tableRemoteView.addObserver(this.gameController);
                //this.weaponRemoteView.addObserver(this.gameController);
                //this.powerUpRemoteView.addObserver(this.gameController);
                boolean goOut = false;
                boolean firstTime = true;

                synchronized (gameController.getStop()) {

                    //gameController.addPlayers("mimmo"); gameController.getPlayers().get(0).setColor("gray");
                    //gameController.addPlayers("eta"); gameController.getPlayers().get(1).setColor("blue");
                    //gameController.addPlayers("beta"); gameController.getPlayers().get(2).setColor("yellow");
                    //gameController.setGameStarted(true);
                    do {

                        if ( !gameController.getGameStarted() ) {

                            this.gameController.askForNickname(this.playerRemoteView, firstTime, gameController.getPlayers());
                            String s = this.playerRemoteView.waitForNickname();

                            //FIRST PLAYER
                            if (gameController.getPlayers().isEmpty()) {

                                goOut = true;
                                gameController.addPlayers(s);
                                this.nickname = s;
                                gameController.sendOk(this.playerRemoteView);

                                boolean duplicated = false;
                                boolean canGoOut = false;
                                firstTime = true;
                                logger.log(Level.INFO, "{PlayerThread "+ this.nickname +"} has memorized the nickname!");
                                while ( !canGoOut ) {

                                    canGoOut = true;
                                    this.gameController.askForColor( this.playerRemoteView, firstTime, duplicated, gameController.getPlayers() );
                                    String si = this.playerRemoteView.waitForColor();
                                    duplicated = false;
                                    if ( !si.equals("blue") &&  !si.equals("yellow") &&  !si.equals("purple") &&  !si.equals("green") &&  !si.equals("gray") ) {
                                        canGoOut = false;
                                        firstTime = false;
                                        gameController.sendNotOk(this.playerRemoteView);
                                    }
                                    else {
                                        for (Player e : gameController.getPlayers()) {

                                            if (e.getCharacter().equals(s)) {
                                                canGoOut = false;
                                                firstTime = false;
                                                duplicated = true;
                                                gameController.sendNotOk(this.playerRemoteView);
                                            }
                                        }
                                        if ( canGoOut ) {
                                            gameController.getPlayers().get(0).setCharacter(si);
                                            gameController.sendOk(this.playerRemoteView);
                                        }
                                    }

                                }
                                logger.log(Level.INFO, "{PlayerThread "+ this.nickname +"} has memorized the character!");
                                this.gameController.sendYouAreFirstPlayer(this.playerRemoteView);
                                //if you to choose skull
                                //if ( this.gameController.getGameboard().getKillshotTrack().isEmpty() ) {
                                //if ( true ) {
                                    //this.gameController.sendThereAreNotSkull(this.playerRemoteView);
                                    canGoOut = false;
                                    firstTime = true;
                                    //you have to choose the number of the skull
                                    while (!canGoOut) {
                                        canGoOut = true;
                                        this.gameController.askForSkull(this.playerRemoteView, firstTime);
                                        String ss = this.playerRemoteView.waitForSkull();

                                        if (!ss.equals("five") && !ss.equals("six") && !ss.equals("seven") && !ss.equals("eight")) {
                                            firstTime = false;
                                            canGoOut = false;
                                            gameController.sendNotOk(this.playerRemoteView);
                                        } else {
                                            this.gameController.initializeKillShotTrack(ss);
                                            gameController.sendOk(this.playerRemoteView);
                                        }
                                    }
                                //}
                                //else {
                                 //   this.gameController.sendThereAreSkull(this.playerRemoteView);
                                //}


                                //you have to choose the number of the map
                                //if ( this.gameController.getGameboard().getMap().getList().get(0).isEmpty() ) {

                                    //this.gameController.sendThereIsNotMap(this.playerRemoteView);
                                    canGoOut = false;
                                    firstTime = true;
                                    while (!canGoOut) {
                                        canGoOut = true;
                                        this.gameController.askForMap(this.playerRemoteView, firstTime);
                                        String nmap = this.playerRemoteView.waitForMap();

                                        if (!nmap.equals("zero") && !nmap.equals("one") && !nmap.equals("two") && !nmap.equals("three")) {
                                            firstTime = false;
                                            canGoOut = false;
                                            gameController.sendNotOk(this.playerRemoteView);
                                        } else {
                                            this.gameController.createMap(nmap);
                                            gameController.sendOk(this.playerRemoteView);
                                        }
                                    }

                                //}
                                //else {
                                 //   this.gameController.sendThereIsMap(this.playerRemoteView);
                                //}

                            } else {
                                //OTHERS PLAYERS
                                if ( this.gameController.getGameStarted() ) {

                                    this.gameController.sendOut(this.playerRemoteView);
                                    throw new NoSuchElementException ();
                                }
                                goOut = true;
                                for (Player p : gameController.getPlayers()) {

                                    if (p.getNickname().equals(s)) {
                                        goOut = false;
                                        firstTime = false;
                                        gameController.sendNotOk(this.playerRemoteView);
                                    }
                                }
                                if (goOut) {
                                    gameController.addPlayers(s);
                                    this.nickname = s;
                                    gameController.sendOk(this.playerRemoteView);

                                    boolean canGoOut = false;
                                    firstTime = true;
                                    boolean duplicated = false;

                                    while ( !canGoOut ) {

                                        canGoOut = true;
                                        this.gameController.askForColor(this.playerRemoteView, firstTime, duplicated, gameController.getPlayers());
                                        String si = this.playerRemoteView.waitForColor();
                                        duplicated = true;
                                        if ( this.gameController.getGameStarted() ) {

                                            this.gameController.sendOut(this.playerRemoteView);
                                            throw new NoSuchElementException ();
                                        }

                                        if ( !si.equals("blue") &&  !si.equals("yellow") &&  !si.equals("purple") &&  !si.equals("green") &&  !si.equals("gray") ) {
                                            canGoOut = false;
                                            firstTime = false;
                                            gameController.sendNotOk(this.playerRemoteView);
                                        }
                                        else {
                                            for (Player e : gameController.getPlayers()) {

                                                if ( e.getCharacter().equals(si)) {
                                                    canGoOut = false;
                                                    firstTime = false;
                                                    duplicated = true;
                                                    gameController.sendNotOk(this.playerRemoteView);
                                                }
                                            }
                                            if( canGoOut ) {
                                                //add color
                                                for (Player player : gameController.getPlayers()) {

                                                    if ( player.getNickname().equals(this.nickname) ) {
                                                        player.setCharacter(si);
                                                        gameController.sendOk(this.playerRemoteView);
                                                    }

                                                }

                                            }
                                        }

                                    }
                                }

                                this.gameController.sendYouAreNotFirstPlayer(this.playerRemoteView);

                                if ( this.gameController.getGameStarted() ) {

                                    this.gameController.sendOut(this.playerRemoteView);
                                    throw new NoSuchElementException ();
                                }
                            }

                        }
                        else {
                            boolean gooutside = false;
                            boolean firstTimeRe = true;

                            while ( !gooutside ) {

                                gameController.sendReconnection(firstTimeRe, playerRemoteView);
                                String s = playerRemoteView.waitForNickname();
                                firstTimeRe = false;

                                for (Player p : gameController.getPlayers()) {

                                    if (p.getNickname().equals(s)) {
                                        gooutside = true;
                                        goOut = true;
                                        this.nickname = s;

                                    }
                                }

                            }
                        }

                    } while (!goOut);

                }
                //announcing the number of missed player
                if (gameController.getPlayers().size() < 5) {

                    System.out.println("Waiting for others " + (5 - gameController.getPlayers().size()) + " player\n");

                    for (Player p : gameController.getPlayers()) {

                        System.out.println(p.getNickname());
                    }
                }

                this.gameController.searchPlayer(this.nickname).addObserver(this.gameController);

                logger.log(Level.INFO, "{ PlayerThread } loop until there are 5 players or the timer finishes" );


                boolean goOutTheLoop = false;

                while ( gameController.getPlayers().size() < 5 && !goOutTheLoop) {

                    synchronized (gameController.getTimerPingThread()) {

                        if ( gameController.getTimerPingThread().getTimePingDone() && gameController.getTimerPingThread().isOn()) {

                            gameController.sendPing(playerRemoteView);
                            logger.log(Level.INFO, "{ PlayerThread " +  this.nickname + "} sends ping ");
                            string = playerRemoteView.waitForPong();
                            logger.log(Level.INFO, "{ PlayerThread " +  this.nickname + "} receives pong ");
                            gameController.getTimerPingThread().setOn(false);
                            gameController.getTimerPingThread().deleteTimer();

                        }

                        if( !gameController.getTimerPingThread().isOn() ) {
                            gameController.getTimerPingThread().run();
                            gameController.getTimerPingThread().setOn(true);
                        }

                    }


                    synchronized ( gameController.getTimerThread() ) {

                        //not enough players but the timer is on
                        if ( gameController.getPlayers().size() < 3 && gameController.getTimerThread().getOn() ) {
                            logger.log(Level.INFO, "{PlayerThread "+ this.nickname +"} has stopped the timer!");
                            gameController.getTimerThread().deleteTimer();
                            gameController.getTimerThread().setTimerDone(false);
                            gameController.getTimerThread().setOn(false);
                        }
                        //enough players but the timer is off
                        if ( gameController.getPlayers().size() >= 3 && !gameController.getTimerThread().getOn()
                                && gameController.getTimerThread().getTurnTime() == 0 && !gameController.getPlayers().get(2).getCharacter().equals("null") ) {
                            logger.log(Level.INFO, "{PlayerThread "+ this.nickname +"} has started the timer!");
                            gameController.getTimerThread().run();
                            gameController.getTimerThread().setOn(true);
                        }
                        //are there enough players when the timer id done?
                        if(gameController.getPlayers().size() >= 3 && gameController.getTimerThread().getTimerDone() && !goOutTheLoop ){
                            //gameController.sendPing(playerRemoteView);
                            logger.log(Level.INFO, "{ PlayerThread " +  this.nickname + "} sends the ok ping ");
                            //string = playerRemoteView.waitForPong();
                            logger.log(Level.INFO, "{ PlayerThread " +  this.nickname + "} receives the ok pong ");
                            goOutTheLoop = true;
                        }
                    }

                }

                synchronized ( gameController.getTimerThread() ) {

                    if (!gameController.getTimerThread().getTimerDone() && gameController.getTimerThread().getOn()) {

                        gameController.getTimerThread().deleteTimer();
                    }

                    gameController.getTimerThread().setOn(false);
                    gameController.setGameStarted(true);
                    //setting the timer for the game
                    gameController.getTimerThread().setGame();
                    gameController.setTimerThreadToTheGame();

                }



                logger.log(Level.INFO, "{ PlayerThread "+ this.nickname +"} is start to game" );
                // the game can start!!
                this.startGame();


            } catch ( NoSuchElementException | IllegalStateException ex ) {

                synchronized (gameController.getStopArray()){

                    logger.log(Level.INFO, "{ PlayerThread " + this.nickname + "} is removed: " + ex.toString());
                    gameController.removePlayers(this);
                }
            }
        }

    }//END of RUN

    private void startGame() {

        if( gameController.getPlayers().get(0).getNickname().equals(this.nickname) ) {
            gameController.setFirstPlayer();

            for (Player p : gameController.getPlayers()) {
                if (p.getNickname().equals(this.nickname)) {
                    gameController.getTurnOf().setPlayer(p);
                }
            }

        }

        gameController.createAmmo();
        gameController.createWeapon();
        gameController.createPowerUp();

        try {
            //send the string "start" to activate a new scene in the client
            this.gameController.sendStartGame(this.playerRemoteView);
            //send the whole model so every client can memorize it in a view to handle the ShowEv
            this.gameController.sendAllModel(this.playerRemoteView);
            this.gameController.getTurnOf().setPlayer(this.gameController.getPlayers().get(0));

            //HERE THE GAME IS ON
            while ( !gameController.getGameover() ) {

                while ( !gameController.getTurnOfPlayer().getNickname().equals(this.nickname) ) {

                    if ( !this.gameController.getTimerThread().getOn() ) {
                        this.gameController.getTimerThread().run();
                        this.gameController.getTimerThread().setOn(true);
                    }

                    if ( this.gameController.getTimerThread().getTimerDone() ) {
                        //how to handle the the timerDone == TRUE??
                        //bisogna buttare fuori questo player
                    }
                }

                ActionEv actionEv = playerRemoteView.waitForAction();
                actionEv.setPlayerNickname(this.nickname);
                gameController.handleEvent(actionEv);

                if ( gameController.getTurnOf().getUsedAction() == 2 ) {
                    //cambio turno al giocatore
                }

            }

        } catch (NoSuchElementException | IllegalStateException ex) {

            synchronized (gameController.getStopArray()){

                logger.log(Level.INFO, "{ PlayerThread " + this.nickname + "} is removed: " + ex.toString());
                //gameController.removePlayerThread(this);
            }

        }

    }

    /**
     * this methods set the new player's status
     * @param status the new player's status
     */
    public void setSuspended( boolean status) {
        this.suspended = status;
    }//END of SET SUSPENDED

    public String getNickname() {
        return nickname;
    }

    public void setGameController(Game controller) {
        this.gameController = controller;
    }

    public PlayerRemoteView getPlayerRemoteView() {
        return this.playerRemoteView;
    }


}//END of CLASS PLAYER THREAD
