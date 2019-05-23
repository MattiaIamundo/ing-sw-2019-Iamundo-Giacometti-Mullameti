package it.polimi.sw2019.network;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.PlayerRemoteView;
import it.polimi.sw2019.view.PowerUpRemoteView;
import it.polimi.sw2019.view.TableRemoteView;
import it.polimi.sw2019.view.WeaponRemoteView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.sw2019.network.Socket.ServerSocket.gameLock;
import static it.polimi.sw2019.network.Socket.ServerSocket.otherPlayerConnected;

public class PlayerThread implements Runnable {

    //client connection
    private Socket connection;
    //input from client
    private Scanner input;
    //output to client
    private PrintWriter output;
    //the number of the player ( 1 to 5 )
    private int playerNumber;
    //the player
    private Player myplayer;
    //if the thread is suspended
    private boolean suspended = true;
    //to contain what the player write ( his move )
    private String string = "null";
    //the number of players in the game
    private int totalPlayer;
    //the logger
    private static final Logger logger = Logger.getLogger( PlayerThread.class.getName() );

    //the game's Remote View
    private PlayerRemoteView playerRemoteView;
    private TableRemoteView tableRemoteView;
    private WeaponRemoteView weaponRemoteView;
    private PowerUpRemoteView powerUpRemoteView;

    /**
     * this is the constructor
     * @param socket the socket reference
     * @param number the number of player
     * @param var the number of the default player total
     */
    public PlayerThread(Socket socket, int number, int var ){
        //set the player's number and the socket
        playerNumber = number;
        connection = socket;
        totalPlayer = var;
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

        try {
            //writing to the player
            output.println( "You are connected, please wait for the other players..." );
            output.flush();
            output.println( "You are the " + (playerNumber) + " player!" );
            output.flush();

            //announcing the number of missed player
            if (playerNumber < totalPlayer ) {

                System.out.println("Waiting for others " + (totalPlayer - (playerNumber) ) + " player\n");
            }

            gameLock.lock();

            try {

                while ( suspended ) {
                    //waiting all the others players
                    otherPlayerConnected.await();
                }

            } catch (InterruptedException e) {

                logger.log(Level.WARNING, e.toString(), e);
                Thread.currentThread().interrupt();

            } finally {

                gameLock.unlock();
            }

            // Player's initialization

            System.out.println("It's not your turn, please wait!\n");
            output.println("It's not your turn, please wait!\n");
            output.flush();

        }
        finally{

            try{
                //the client connection is closed
                System.out.println("The connection with the player " + (playerNumber) + " is closed!\n");
                this.connection.close();
                this.input.close();
                this.output.close();

            }catch(IOException e){

                e.printStackTrace();
                System.exit(1);
            }
        }

    }//END of RUN

    /**
     * this methods set the new player's status
     * @param status the new player's status
     */
    public void setSuspended (boolean status) {
        this.suspended = status;
    }//END of SET SUSPENDED

    public int getPlayerNumber () {
        return this.playerNumber;
    }

}//END of CLASS PLAYER
