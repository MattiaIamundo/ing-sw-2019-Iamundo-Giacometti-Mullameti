package it.polimi.sw2019.network.Socket;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerSocket extends JFrame implements Serializable {

    //the serial number
    private static final long serialVertionUID = 11111;
    //the port number
    private static final int portNumber = 12345;
    //the server
    private java.net.ServerSocket server;
    // the service which make started the game
    private ExecutorService runGame;
    //the threads
    private Player[] players = new Player[3];
    //the "main" lock
    private Lock gameLock;
    //the condition which set the first time which in the three player are connected
    private Condition otherPlayerConnected;
    //the condition which set the turn
    private Condition otherPlayersTurn;
    // if the game is over set this true
    private boolean gameOver = false;

//----------- THIS IS THE FIRST SERVER VERSION, MADE ONLY TO TEST THE CONNECTION BETWEEN SERVER AND CLIENT ---------
// -----------                   AND IT IS SET ONLY FOR THREE PLAYERS               ---------
    /**
     * this is the server's constructor
     */
    public ServerSocket(){

        //set the number of thread (players)
        runGame = Executors.newFixedThreadPool(3);
        //create the game's lock
        gameLock = new ReentrantLock();

        //condition variable: all players have to be connected
        otherPlayerConnected = gameLock.newCondition();

        //condition variable: this is the other's player turn
        otherPlayersTurn = gameLock.newCondition();

        try{
            //set the server, the number of player is set to 3
            server = new java.net.ServerSocket( portNumber, 3);
            System.out.println("The server is ready!\n");

        }catch (IOException e) {

            e.printStackTrace();
            System.exit(1);

        }
        //will set the GUI
    }// END of CONSTRUCTOR


    /**
     * this method create the threads for the players,
     * and locking the game until there are three clients
     * connected
     */
    public void execute (){
        //it waits that every client is connected
        for (int i = 0; i < players.length; i++){

            System.out.println("The server is waiting for " + (3-i) + " players...\n");

            try{
                //wait connection, create player, set the thread
                players[i] = new Player(server.accept(), i);
                //thread is set
                runGame.execute( players[i] );
                System.out.println("The #" + (i+1) + " player is set\n");

            }catch (IOException e){

                e.printStackTrace();
                System.exit(1);
            }
        }

        //the game is locked until all players are connected
        gameLock.lock();

        //if all players are connected
        if (  ( players[0] != null && players[1] != null && players[2] != null )  ){

            try{

                for (int i = 0; i < players.length; i++){
                    //this player is ok
                    players[i].setSuspended(false);
                }
                //one thread is awaken
                otherPlayerConnected.signalAll();
            }finally{
                //the game cans continue
                gameLock.unlock();
                System.out.println("The game cans start!\n");

            }
        }

    }// END of EXECUTE


    /**
     * this method set the turn for the thread
     * @param playerNumber the thread's number
     */
    public void endTurn (int playerNumber) {
        //THIS IS FOR 3 PLAYERS
        if (playerNumber < 2) {

            players[playerNumber].setSuspended(true);
            players[playerNumber+1].setSuspended(false);
        }else {

            players[playerNumber].setSuspended(true);
            players[0].setSuspended(false);
        }
        //to wake up one thread
        otherPlayersTurn.signal();

    }//END of END TURN


    /**
     * this method return true
     * if the game is over or not
     * @return gameOver
     */
    public boolean isGameOver(){
        return this.gameOver;
    }//END of isGameOver

//      ------  this is the thread to run   ------

    private class Player implements Runnable {

        //client connection
        private Socket connection;
        //input from client
        private Scanner input;
        //output to client
        private PrintWriter output;
        //the number of the player
        private int playerNumber;
        //the player's nickname
        private String nickname;
        //if the thread is suspended
        private boolean suspended = true;
        //to contain what the player wrote ( his move )
        private String string = "null";


        /**
         * this is the constructor
         * @param socket the socket reference
         * @param number the number of player
         */
        public Player(Socket socket, int number){
            //set the player's number and the socket
            playerNumber = number;
            connection = socket;

            try{
                //what the client is writing
                input = new Scanner( connection.getInputStream() );
                //what the server is sending to client
                output = new PrintWriter( connection.getOutputStream() );

            }catch(IOException e){

                e.printStackTrace();
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
                output.println( "You are the " + (playerNumber+1) + " player!" );
                output.flush();

                //announcing the number of missed player
                if (playerNumber < 2 ) {

                    System.out.println("Waiting for others " + (2 - playerNumber) + " player\n");
                }

                gameLock.lock();

                try {

                    while ( suspended ) {
                        //waiting all the others players
                        otherPlayerConnected.await();
                    }

                } catch (InterruptedException e) {

                    e.printStackTrace();
                } finally {

                    gameLock.unlock();
                }
                //announcing the game cans start to the players
                output.println("The players are connected so the game cans start!");
                output.flush();

//-------------     HERE IS THE MAIN LOOP FOR THE GAME      ----------------

                //loop where the game is on air
                while ( !isGameOver() ) {

                    gameLock.lock();

                    while (suspended) {

                        try {

                            otherPlayersTurn.await();

                        }catch (InterruptedException e) {

                            e.printStackTrace();
                        }
                        finally {

                            gameLock.unlock();
                        }

                    }//END of WHILE suspended

                    System.out.println("Turn of #" + (playerNumber+1) + " player!\n");

                    //WHILE turnOf
                    while ( true ) {

                        output.println("It's your turn, so you can write your move!\n");
                        output.flush();

                        //the player can write something
                        if ( input.hasNextLine() ) {
                            //what the client is writing
                            string = input.nextLine();

                        } else {
                            //options to reconnect to the game
                        }

                        //the player want to disconnect from the game
                        if ( string.equals("quit") ) {

                            System.out.println("#" + (playerNumber + 1) + ")" + string + "\n");
                            output.println("Received: " + string + "\n");
                            output.flush();
                            //change the turn
                            endTurn(playerNumber);
                            output.println("Thanks for gaming!\n");
                            output.flush();
                            break;

                        }
                        //the player want to finish the turn, in future it will be automatic
                        else if ( string.equals("end turn") ) {

                            System.out.println("#" + (playerNumber + 1) + ")" + string + "\n");
                            output.println("Received: " + string);
                            output.flush();
                            //change the turn
                            endTurn(playerNumber);
                            output.println("Now wait your next turn, please!\n");
                            output.flush();
                            break;

                        }
                        //to signal that the server has received the message
                        else {

                            System.out.println("#" + (playerNumber + 1) + ")" + string + "\n");
                            output.println("Received: " + string);
                            output.flush();
                            //here there is the analyse of the moves
                        }

                    }//END of WHILE turnOf

                    if ( string.equals("quit") ) {
                        break;
                    }

                }//END of the WHILE isGameOver

            }
            finally{

                try{
                    //the client connection is closed
                    System.out.println("The connection with the player " + (playerNumber+1) + " is closed!\n");
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
            //setting the suspended value
            this.suspended = status;

        }//END of SET SUSPENDED

    }//END of PRIVATE CLASS PLAYER


    public static void main (String[] args){
        //i set the server
        ServerSocket application = new ServerSocket();
        //this is for the GUI: application.detDefaultCloseOperation(JFrame);
        application.execute();
    }
}