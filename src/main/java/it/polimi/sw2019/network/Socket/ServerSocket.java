package it.polimi.sw2019.network.Socket;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.exception.CancellPlayerException;
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

public class ServerSocket extends JFrame implements Runnable, Serializable {

    //the serial number
    private final long serialVertionUID = 11111;
    //the port number
    private final int portNumber = 12345;
    //the server
    private java.net.ServerSocket server;
    //
    ExecutorService runGame = Executors.newFixedThreadPool(5);
    //
    ExecutorService others = Executors.newCachedThreadPool();
    //the threads
    private ArrayList <PlayerThread> players = new ArrayList <>(5);
    //the controller
    private Game socketController;
    //the "main" lock
    private Lock gameLock;
    //the condition which set the first time which in the three player are connected
    private Condition otherPlayerConnected;
    //the condition which set the turn
    private Condition otherPlayersTurn;
    //the logger for debug
    private static final Logger logger = Logger.getLogger( ServerSocket.class.getName() );

    /**
     * this is the server's constructor
     */
    public ServerSocket( Game sockContr){

        //create the game's lock
        gameLock = new ReentrantLock();
        //condition variable: all players have to be connected
        otherPlayerConnected = gameLock.newCondition();
        //condition variable: it the turn of another player
        otherPlayersTurn = gameLock.newCondition();

        this.socketController = sockContr;

        try{
            //set the server, the number of player is set
            server = new java.net.ServerSocket( portNumber, 5);
            logger.log( Level.INFO, "{ ServerSocket } is initialized!\n");

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
    public void run(){

        logger.log( Level.INFO, "{ ServerSocket } is waiting for the clients!\n");

        while ( !socketController.getGameover() ) {

            try {
                //accept a player
                PlayerThread p = new PlayerThread( this.server.accept(), this.socketController);

                if ( socketController.getPlayers().size() < 5 ) {

                    if (socketController.getGameover()) {
                        //don't take the request and finish the server
                        others.execute(p);
                        break;

                    } else if (socketController.getGameStarted()) {
                        //implements how reinsert the player
                        runGame.execute(p);
                    }
                    else {
                        getPlayers().add(p);
                        runGame.execute(p);
                        logger.log( Level.INFO, "process { ServerSocket } is setting a player\n");
                    }

                }
                else {
                    others.execute(p);
                }

            } catch (IOException e) {
                logger.log(Level.SEVERE, e.toString(), e);
                logger.log(Level.INFO, "{ServerSocket} a IOException: " + e.toString());
                e.printStackTrace();

            }

        }//END of WHILE

    }// END of RUN

  /*  public void endTurn(int playerNumber) {
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
*/
 /*   public void endTurnQuit(int playerNumber) {

        endTurn(playerNumber);
        players.set(playerNumber-1, null);

    }//END of END TURN QUIT
*/

    public List <PlayerThread> getPlayers() {
        return players;
    }

    public void setSocketController(Game cont ) {
        this.socketController = cont;
    }

    public java.net.ServerSocket getServer() {
        return this.server;
    }

}
