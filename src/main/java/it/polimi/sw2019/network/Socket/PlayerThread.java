package it.polimi.sw2019.network.Socket;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.controller.MultiGame;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.client_event.YourTurnEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.PlayerRemoteView;
import it.polimi.sw2019.view.PowerUpRemoteView;
import it.polimi.sw2019.view.TableRemoteView;
import it.polimi.sw2019.view.WeaponRemoteView;
import javafx.application.Platform;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PlayerThread implements Runnable {

    //client connection
    private Socket connection;
    //input from client
    private Scanner input;
    //output to client
    private PrintWriter output;
    //the player's nickname
    private String nickname = "pippo";
    //to contain what the player write ( his actions )
    private String string;
    //the controller
    private Game gameController;

    //the logger
    private static final Logger logger = Logger.getLogger( PlayerThread.class.getName() );

    //the game's Remote View
    private PlayerRemoteView playerRemoteView;
    private TableRemoteView tableRemoteView;
    private WeaponRemoteView weaponRemoteView;
    private PowerUpRemoteView powerUpRemoteView;

    /**
     * set the remote view, the connection to the socket and
     * the controller
     * @param socket the connection with the socket
     * @param controller the game's controller
     */
    public PlayerThread(Socket socket, Game controller){
        //set the player's number and the socket
        connection = socket;
        string = "bho";
        gameController = controller;
        playerRemoteView = new PlayerRemoteView(socket,controller);
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

                        logger.log(Level.SEVERE, e.toString(), e.getMessage());
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

                            logger.log(Level.SEVERE, e.toString(), e.getMessage());
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

                logger.log(Level.SEVERE, e.toString(), e.getMessage());
                System.exit(1);
            }
        }
        else {

            //you are connected with the game
            try {

                boolean goOut = false;
                boolean firstTime = true;

                synchronized (gameController.getStop()) {

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

                                    canGoOut = false;
                                    firstTime = true;
                                    while (!canGoOut) {
                                        canGoOut = true;
                                        this.gameController.askForMap(this.playerRemoteView, firstTime);
                                        String nmap = this.playerRemoteView.waitForMap();
                                        logger.log(Level.INFO, "{playerthread} receives this map" + nmap);
                                        if (!nmap.equals("zero") && !nmap.equals("one") && !nmap.equals("two") && !nmap.equals("three")) {
                                            firstTime = false;
                                            canGoOut = false;
                                            gameController.sendNotOk(this.playerRemoteView);
                                        } else {
                                            this.gameController.createMap(nmap);
                                            this.gameController.getGameboard().setNrMap(nmap);
                                            gameController.sendOk(this.playerRemoteView);
                                        }
                                    }


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

                this.gameController.searchPlayer(this.nickname).addObserver(this.playerRemoteView);
                this.playerRemoteView.setNickname(this.nickname);


                logger.log(Level.INFO, "{ PlayerThread } loop until there are 5 players or the timer finishes" );

                while ( gameController.getPlayers().size() < 5 && !gameController.getTimerThread().getTimerDone()) {

                    synchronized (gameController.getTimerPingThread()) {

                        if ( gameController.getTimerPingThread().getTimePingDone() && gameController.getTimerPingThread().isOn()) {

                            gameController.sendPing(playerRemoteView);
                            logger.log(Level.INFO, "{ PlayerThread " +  this.nickname + "} sends ping ");
                            string = playerRemoteView.waitForPong();
                            logger.log(Level.INFO, "{ PlayerThread " +  this.nickname + "} receives pong ");
                            gameController.getTimerPingThread().deleteTimer();
                            gameController.getTimerPingThread().setTimerPingDone(false);
                            gameController.getTimerPingThread().setOn(false);



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
                                && !gameController.getPlayers().get(2).getCharacter().equals("null") ) {
                            logger.log(Level.INFO, "{PlayerThread "+ this.nickname +"} has started the timer!");
                            gameController.getTimerThread().run();
                            gameController.getTimerThread().setOn(true);
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

    /**
     * this method set the main things to start the game,
     * and there is the game loop
     */
    private void startGame() {

        try {
            gameController.getPlayerThreads().add(this);
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
            //fill the table in space generation and in space ammo with weapon and ammo
            if( this.nickname.equals(this.gameController.getPlayers().get(0).getNickname() ) ) {

                gameController.setWeapon();
                gameController.setAmmo(this.gameController.getGameboard().getNrMap());
                //gameController.setPlayerPositionRandom();

            }

            //send the string "start" to activate a new scene in the client
            this.gameController.sendStartGame(this.playerRemoteView);
            //send the whole model so every client can memorize it in a view to handle the ShowEv
            this.gameController.sendAllModel(this.playerRemoteView);
            this.gameController.getTurnOf().setPlayer(this.gameController.getPlayers().get(0));
            this.playerRemoteView.addObserver(this.gameController);

            if( this.gameController.getTurnOf().getPlayer().getNickname().equals(this.nickname)) {
                YourTurnEv yourTurnEv = new YourTurnEv();
                yourTurnEv.setNickname(this.nickname);
                //this.gameController.update(yourTurnEv);
            }
            //HERE THE GAME IS ON
            while ( !gameController.getGameover() ) {

                while ( !gameController.getTurnOfPlayer().getNickname().equals(this.nickname) ) {

                    synchronized (this.gameController.getTimerThread()) {

                        if( this.gameController.getTimerThread().getTimerDone() && this.gameController.getPlayerThreads().size() < 3) {
                            //game over
                            this.gameController.setGameover(true);
                            //this.gameController.sendGameOver();
                        }

                        if ( !this.gameController.getTimerThread().getOn() ) {
                            this.gameController.getTimerThread().run();
                            this.gameController.getTimerThread().setOn(true);
                            this.gameController.getTimerThread().setTimerDone(false);
                        }

                        if ( this.gameController.getTimerThread().getTimerDone() ) {
                            //send out to the player
                            this.gameController.removePlayerThread(this.gameController.searchPlayerThread(this.nickname));
                            this.gameController.getTurnOf().setUsedAction(0);
                            changePlayerTurn();
                            this.gameController.getTimerThread().setOn(false);
                            this.gameController.getTimerThread().setTimerDone(false);
                        }


                    }
                }

                //it's my turn so i can wait for an event from client
                if (gameController.getTurnOfPlayer().getNickname().equals(this.nickname)) {

                    this.playerRemoteView.waitForAction();
                    //if i did two action i have to change the turn
                    if ( gameController.getTurnOf().getUsedAction() == 2  ) {
                        //next player
                        this.gameController.getTurnOf().setUsedAction(0);
                        changePlayerTurn();
                    }
                }




            }

        } catch (NoSuchElementException | IllegalStateException ex) {

            synchronized (gameController.getStopArray()){

                logger.log(Level.INFO, "{ PlayerThread " + this.nickname + "} is removed: " + ex.toString());
                gameController.removePlayerThread(this);
            }

        }

    }

    /**
     * return the nickname of player
     * @return player's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * set the player for the next turn
     */
    private void changePlayerTurn() {

        //this.gameController.sendNotYourTurn(this.playerRemoteView);
        for (int i = 0; i < this.gameController.getPlayers().size(); i++) {

            if ( this.gameController.getPlayers().get(i).getNickname().equals(this.nickname) ) {

                if ( i == this.gameController.getPlayers().size() - 1 ) {
                    gameController.getTurnOf().setPlayer( this.gameController.getPlayers().get(0) );
                    //this.gameController.sendYourTurn();
                }
                else {
                    gameController.getTurnOf().setPlayer(this.gameController.getPlayers().get(i+1));
                }
            }
        }
    }

    /**
     * return the remote view associates to player
     * @return the player's remote view
     */
    public PlayerRemoteView getPlayerRemoteView() {
        return this.playerRemoteView;
    }


}//END of CLASS PLAYER THREAD
