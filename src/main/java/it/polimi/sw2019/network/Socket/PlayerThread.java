package it.polimi.sw2019.network.Socket;

import it.polimi.sw2019.controller.Game;
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
    //
    private String nickname = "pippo";
    //to contain what the player write ( his move )
    private String string;
    //the lock
    private Lock gameLocker;
    //the condition to put the thread in wait
    private Condition turnOfOtherPlayers;
    private Condition otherPlayerConnected;
    //the controller
    private Game gameController;
    //the logger
    private static final Logger logger = Logger.getLogger( PlayerThread.class.getName() );

    //the game's Remote View
    private PlayerRemoteView playerRemoteView;
    private TableRemoteView tableRemoteView;
    private WeaponRemoteView weaponRemoteView;
    private PowerUpRemoteView powerUpRemoteView;


    public PlayerThread(Socket socket, Game controller){
        //set the player's number and the socket
        connection = socket;
        suspended = true;
        string = "bho";
        gameLocker = new ReentrantLock();
        turnOfOtherPlayers = gameLocker.newCondition();
        otherPlayerConnected = gameLocker.newCondition();
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
                logger.log(Level.FINE, " {ServerSocket} The connection with the player  is closed!\n");
                this.connection.close();
                this.input.close();
                this.output.close();

            }catch(IOException e) {

                e.printStackTrace();
                System.exit(1);
            }
        }
        else {


            //you are connected with the game
            try {

                this.playerRemoteView.addObserver(this.gameController);
                this.tableRemoteView.addObserver(this.gameController);
                this.weaponRemoteView.addObserver(this.gameController);
                this.powerUpRemoteView.addObserver(this.gameController);
                boolean goOut = false;
                boolean firstTime = true;

                synchronized (gameController.getStop()) {

                    do {

                        if ( !gameController.getGameStarted() ) {

                            this.gameController.askForNickname(this.playerRemoteView, firstTime, gameController.getPlayers());
                            String s = this.playerRemoteView.waitForNickname();

                            if (gameController.getPlayers().isEmpty()) {
                                goOut = true;
                                gameController.addPlayers(s);
                                this.nickname = s;

                            } else {
                                goOut = true;
                                for (Player p : gameController.getPlayers()) {

                                    if (p.getNickname().equals(s)) {
                                        goOut = false;
                                        firstTime = false;
                                    }
                                }
                                if (goOut) {
                                    gameController.addPlayers(s);
                                    this.nickname = s;
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

                gameController.sendOk(playerRemoteView);

                //announcing the number of missed player
                if (gameController.getPlayers().size() < 5) {

                    System.out.println("Waiting for others " + (5 - gameController.getPlayers().size()) + " player\n");

                    for (Player p : gameController.getPlayers()) {

                        System.out.println(p.getNickname());
                    }
                }

                logger.log(Level.INFO, "{ PlayerThread } loop until there are 5 players or the timer finishes" );

                while (gameController.getPlayers().size() < 5 && !gameController.getTimerThread().getTimerDone()) {

                    gameController.sendPing(playerRemoteView);
                    string = playerRemoteView.waitForPong();

                    synchronized ( gameController.getTimerThread() ) {

                        if ( gameController.getPlayers().size() < 3 && gameController.getTimerThread().getOn()) {
                            logger.log(Level.INFO, "{PlayerThread "+ this.nickname +"} has stopped the timer!");
                            gameController.getTimerThread().deleteTimer();
                            gameController.getTimerThread().setTimerDone(false);
                            gameController.getTimerThread().setOn(false);
                        }

                        if ( gameController.getPlayers().size() >= 3 && !gameController.getTimerThread().getOn() && gameController.getTimerThread().getTurnTime() == 0) {
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

                    gameController.getTimerThread().setTimerDone(false);
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
                    return;
                }
            }
        }
    }//END of RUN



    private void startGame() {

        while ( !gameController.getGameover() ) {


            logger.log(Level.INFO, "{PT} is starting the game\n ");
            gameLocker.lock();

            try {

                while (suspended) {
                    //waiting all the others players
                    turnOfOtherPlayers.await();
                }

            } catch (InterruptedException e) {

                logger.log(Level.WARNING, e.toString(), e);
                Thread.currentThread().interrupt();

            } finally {

                gameLocker.unlock();
            }

            System.out.println("It's not your turn, please wait!\n");
            output.println("It's not your turn, please wait!\n");
            output.flush();
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

    public void setGameLocker(Lock gl) {
        this.gameLocker = gl;
    }

}//END of CLASS PLAYER THREAD
