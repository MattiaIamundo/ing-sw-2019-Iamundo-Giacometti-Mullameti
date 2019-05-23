package it.polimi.sw2019.network.Socket;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.network.PlayerThread;
import it.polimi.sw2019.network.SocketLink;
import it.polimi.sw2019.utility.TimerThread;

import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSocket extends JFrame implements Serializable {

    //the serial number
    private static final long serialVertionUID = 11111;
    //the port number
    private static final int portNumber = 12345;
    //the server
    private java.net.ServerSocket server;
    // the service which make started the game
    private static ExecutorService runGame;
    // the executor which make started the timer
    private ExecutorService startTimer;
    // the executor to the socket link
    private ExecutorService startSocketLink;
    //the threads
    private static ArrayList <PlayerThread> players = new ArrayList <>(5);
    //the "main" lock
    public static Lock gameLock;
    //the condition which set the first time which in the three player are connected
    public static Condition otherPlayerConnected;
    //the condition which set the turn
    private Condition otherPlayersTurn;
    // if the game is over set this true
    private boolean gameOver = false;
    //number of players ( 3 to 5 )
    private int numberThread = 5;
    //the milliseconds that server wait for 5 players
    private int secToTimer = 5000;
    //the game's controller
    private Game controller = new Game();
    //the logger for debug
    private static final Logger logger = Logger.getLogger( ServerSocket.class.getName() );
    //thread to start the timer
    private TimerThread timerThread = new TimerThread();
    //
    private static boolean out = false;

    /**
     * this is the server's constructor
     */
    private ServerSocket( ){

        //set the number of thread (players)
        runGame = Executors.newFixedThreadPool( 5 );
        //set the thread for the timer
        startTimer = Executors.newSingleThreadExecutor();
        //set the thread for the socket linker
        startSocketLink = Executors.newSingleThreadExecutor();
        //create the game's lock
        gameLock = new ReentrantLock();
        //condition variable: all players have to be connected
        otherPlayerConnected = gameLock.newCondition();
        //condition variable: it the turn of another player
        otherPlayersTurn = gameLock.newCondition();

        try{
            //set the server, the number of player is set
            server = new java.net.ServerSocket( portNumber, 5);
            System.out.println("The server is ready!\n");
            logger.log( Level.FINE, "process { ServerSocket } is initialized");

        }catch (IOException e) {

            logger.log( Level.SEVERE, e.toString(), e);
            System.exit(1);

        }

    }// END of CONSTRUCTOR

    /**
     * this method create the threads for the players,
     * and locking the game until there are three clients
     * connected
     */
    private void execute (){

        System.out.println("The server is waiting for five players...\n");
        logger.log( Level.FINE, "process { ServerSocket } is waiting for the clients");

        SocketLink link = new SocketLink(this.server);
        //start the socket linker
        startSocketLink.execute(link);
        //and only that on the startSocketLink executor
        startSocketLink.shutdown();
        //was the timer started?
        boolean b = false;

        while ( players.size() < 5 && !out ) {
            //without this, the client's acquisition don't go on!!!!!
            System.out.println("In the loop\n");
            if ( players.size() >= 3 && !b ) {
                //set the timer
                startTimer.execute(timerThread);
                System.out.println("execute the timer\n");
                //the timer is activated
                b = true;
            }

        }

        if ( players.size() == 5 ) {
           //it the normal situation
        }
        else {
            //there are less players then normal
            System.out.println("Only " + players.size() + " players are connected!\n");
        }

        timerThread.deleteTask();
        timerThread.setGame();
        //the game is locked until all players are connected
        gameLock.lock();

        Iterator <PlayerThread> iterator = players.iterator();
        //if all players are connected
            try{

                while ( iterator.hasNext() ) {
                    iterator.next().setSuspended(false);
                }
                logger.log(Level.FINE, "process { ServerSocket} is calling otherPlayerConnected.signalAll");
                //one thread is awaken
                otherPlayerConnected.signalAll();

            }finally{
                //the game cans continue
                gameLock.unlock();
                System.out.println("The game can start!\n");

            }

    }// END of EXECUTE

    //to accept other request during the game
    public void executeAfter () {
/*
        try {
            int i = 0;
            Player p = new Player (server.accept(), i, numberThread);
            //wait connection, create player, set the thread
            players.add( p );
            //thread is set
            runGame.execute( players.get(i) );
            System.out.println("The #" + (i + 1) + " player is set\n");

        } catch (IOException e) {

            e.printStackTrace();
            System.exit(1);
        }
 */
    }

    /**
     * set the variable out to go outside the loop
     */
    public static void setOut() {
        out = true;
    }

    public static boolean getOut() {
        return out;
    }

    /**
     * this method set the turn for the threads
     * @param playerNumber the thread's number (1 to 5)
     */
    public void endTurn (int playerNumber) {
        //numberThread is the number of players in the game
        //playerNumber is the number of a player runnable
        Iterator <PlayerThread> iterator = players.iterator();
        PlayerThread p;

        //the player is not the last one
        if ( playerNumber < players.size() ) {

            players.get(playerNumber-1).setSuspended(true);
            //check the player to make it not suspended
            while ( iterator.hasNext() ) {
                p = iterator.next();

                if ( p != null ) {

                    int index = players.indexOf(p);
                    players.get(index).setSuspended(false);
                    break;
                }

            }
            //the players after him are null
            if ( !iterator.hasNext() ) {
                iterator = players.iterator();
                //i have to check from the first player
                //now p is at the players's beginning
                while ( iterator.hasNext() ) {
                    p = iterator.next();

                    if ( p != null ) {

                        int index = players.indexOf(p);
                        players.get(index).setSuspended(false);
                        break;
                    }
                }
            }
        }
        //the player is the last one
        else if ( playerNumber == players.size() ) {

            players.get(playerNumber-1).setSuspended(true);
            iterator = players.iterator();

            while ( iterator.hasNext() ) {
                p = iterator.next();

                if ( p != null ) {

                    int index = players.indexOf(p);
                    players.get(index).setSuspended(false);
                    break;
                }
            }
        }

    }//END of END TURN

    /**
     * this method call endTurn and
     * set the player who wrote "quit" to null
     *
     * @param playerNumber the player's number
     */
    public void endTurnQuit (int playerNumber) {

        endTurn(playerNumber);
        players.set(playerNumber-1, null);

    }//END of END TURN QUIT

    /**
     * this method return true
     * if the game is over or not
     * @return gameOver
     */
    public boolean isGameOver(){
        return this.gameOver;
    }//END of isGameOver

    public static ArrayList <PlayerThread> getPlayers () {
        return players;
    }

    public static ExecutorService getRunGame () { return runGame; }

    public static void main (String[] args){
        //i set the server
        ServerSocket application = new ServerSocket();
        //and run it
        application.execute();
        //this is for the GUI: application.detDefaultCloseOperation(JFrame);
    }
}