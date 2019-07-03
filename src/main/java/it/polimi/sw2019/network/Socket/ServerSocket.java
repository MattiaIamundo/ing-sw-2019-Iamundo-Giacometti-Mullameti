package it.polimi.sw2019.network.Socket;

import it.polimi.sw2019.controller.Game;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSocket implements Runnable {

    //the port number
    private static final int portNumber = 12345;
    //the server
    private java.net.ServerSocket server;
    //
    private ExecutorService runGame = Executors.newFixedThreadPool(5);
    //
    private ExecutorService others = Executors.newCachedThreadPool();
    //the threads
    private ArrayList <PlayerThread> players = new ArrayList <>(5);
    //the controller
    private Game socketController;
    //the logger for debug
    private static final Logger logger = Logger.getLogger( ServerSocket.class.getName() );

    /**
     * this is the server's constructor
     */
    public ServerSocket( Game sockContr){

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

}
