package it.polimi.sw2019.network.Socket;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.*;
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
    private ArrayList <Player> players = new ArrayList <>(5);
    //the players' names
    private ArrayList <String> nicknames = new ArrayList <>(5);
    //1 is gaming, 0 is out of the game (disconnected...)
    private boolean[] isInGame = new boolean[5];
    //the "main" lock
    private Lock gameLock;
    //the condition which set the first time which in the three player are connected
    private Condition otherPlayerConnected;
    //the condition which set the turn
    private Condition otherPlayersTurn;
    // if the game is over set this true
    private boolean gameOver = false;
    //number of players ( 3 to 5 )
    private int numberThread = 0;

    //here there is ALL model and the controller

//----------- THIS IS THE FIRST SERVER VERSION, MADE ONLY TO TEST THE CONNECTION BETWEEN SERVER AND CLIENT ---------
    /**
     * this is the server's constructor
     */
    public ServerSocket(int var){

        //set the number of thread (players)
        runGame = Executors.newFixedThreadPool( var );
        //create the game's lock
        gameLock = new ReentrantLock();

        //condition variable: all players have to be connected
        otherPlayerConnected = gameLock.newCondition();

        //condition variable: this is the other's player turn
        //otherPlayersTurn = gameLock.newCondition();

        try{
            //set the server, the number of player is set to var( = 3 to 5)
            server = new java.net.ServerSocket( portNumber, var);
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
     * @param var the number of thread run ( 3 to 5 )
     */
    public void execute (int var){
        //it waits that every client is connected
        //set the number of players
        numberThread = var;
        int i = 1;

        System.out.println("The server is waiting for " + (var) + " players...\n");

        while ( i <= numberThread ) {

            try {
                Player p = new Player (server.accept(), i, numberThread);
                //wait connection, create player, set the thread
                players.add( p );
                //thread is set
                runGame.execute( players.get(i-1) );
                System.out.println("The #" + i + " player is set\n");

            } catch (IOException e) {

                e.printStackTrace();
                System.exit(1);
            }

            i++;
        }

        //the game is locked until all players are connected
        gameLock.lock();

        Iterator <Player> iterator = players.iterator();
        //if all players are connected
            try{

                while ( iterator.hasNext() ) {

                    iterator.next().setSuspended(false);
                }
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
     * this method set the turn for the threads
     * @param playerNumber the thread's number (1 to 5)
     */
    public void endTurn (int playerNumber) {
        //numberThread is the number of players in the game
        //playerNumber is the number of a player runnable
        Iterator <Player> iterator = players.iterator();
        Player p;

        //the player is not the last one
        if ( playerNumber < numberThread ) {

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
        else if ( playerNumber == numberThread ) {

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

        //to wake up one thread
        //otherPlayersTurn.signal();

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

//      ------  this is the thread to run   ------

    private class Player implements Runnable {

        //client connection
        private Socket connection;
        //input from client
        private Scanner input;
        //output to client
        private PrintWriter output;
        //the number of the player ( 1 to 5 )
        private int playerNumber;
        //the player's nickname
        private String nickname;
        //if the thread is suspended
        private boolean suspended = true;
        //to contain what the player write ( his move )
        private String string = "null";
        //the number of players in the game
        private int totalPlayer;


        /**
         * this is the constructor
         * @param socket the socket reference
         * @param number the number of player
         */
        public Player(Socket socket, int number, int var){
            //set the player's number and the socket
            playerNumber = number;
            connection = socket;
            totalPlayer = var;

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

                    //gameLock.lock();

                    while (suspended) {
/*
                        try {

                            otherPlayersTurn.await();

                        }catch (InterruptedException e) {

                            e.printStackTrace();
                        }
                        finally {

                            //gameLock.unlock();
                        }


*/
                    }//END of WHILE suspended

                    System.out.println("Turn of #" + (playerNumber) + " player!\n");

                    //WHILE turnOf
                    while ( true ) {

                        output.println("It's your turn, so you can write your move!\n*)MoveTake\n*)Shoot\n*)Reload");
                        output.flush();

                        //the player can write something
                        if ( input.hasNextLine() ) {
                            //what the client is writing
                            string = input.nextLine();

                        }

                        //the player want to disconnect from the game
                        if ( string.equals("quit") ) {

                            System.out.println("#" + (playerNumber) + ")" + string + "\n");
                            output.println("Received: " + string + "\n");
                            output.flush();
                            //change the turn
                            endTurnQuit(playerNumber);
                            output.println("Thanks for gaming!\n");
                            output.flush();
                            break;

                        }
                        //the player want to finish the turn, in future it will be automatic
                        else if ( string.equals("end turn") ) {

                            System.out.println("#" + (playerNumber) + ")" + string + "\n");
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

                            System.out.println("#" + (playerNumber) + ")" + string + "\n");
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
            //setting the suspended value
            this.suspended = status;

        }//END of SET SUSPENDED

        public int getPlayerNumber () {
            //the player's number
            return this.playerNumber;
        }

    }//END of PRIVATE CLASS PLAYER


    public static void main (String[] args){

        //set the number of player in a casual mode
        int min = 3;
        int max = 5;
        int set = ( max - min ) + 1; //5-3+1=2+1=3
        Random random = new Random();
        int var = random.nextInt(set) + min;// 0,1,2 + 3 = 3,4,5

        //i set the server
        ServerSocket application = new ServerSocket(var);
        //and run it
        application.execute(var);
        //this is for the GUI: application.detDefaultCloseOperation(JFrame);
    }
}