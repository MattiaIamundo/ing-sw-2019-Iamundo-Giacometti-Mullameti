package it.polimi.sw2019.network.Socket;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientSocket extends JFrame implements Runnable, Serializable {

    //the serial number
    private static final long serialVertionUID = 11111;
    //the port number
    private static final int portNumber = 12345;
    //connection to the server
    private Socket connection;
    //input from the server
    private Scanner input;
    //this is for the player input
    private Scanner scanner;
    //output for the server
    private PrintWriter output;
    //the player's number
    private int playerNumber;
    //host name for the server
    private String serverHost;
    //it contains the what the client is writing
    //it is set to null to avoid the exception
    private String string = "null";

//----------- THIS IS THE FIRST CLIENT VERSION, MADE ONLY TO TEST THE CONNECTION BETWEEN SERVER AND CLIENT ---------

    /**
     * this is the constructor
     * @param host the server name
     */
    public ClientSocket(String host){
        //set the server name
        serverHost = host;
        //commands for set the GUI
        startClient();

    }//END of the CONSTRUCTOR

    /**
     * in this method are set the connection with the server,
     * get the streams and run the thread's client
     */
    public void startClient(){

        try{
            //set the connection
            connection = new Socket( InetAddress.getByName(serverHost), portNumber);
            //getting the input and output stream
            input = new Scanner( connection.getInputStream() );
            output = new PrintWriter( connection.getOutputStream() );
            //get the player stream
            scanner = new Scanner( System.in );

        }catch(IOException e){

            e.printStackTrace();
        }
        //it creates and starts the thread for this client
        ExecutorService worker = Executors.newFixedThreadPool( 1 );
        //client is executed
        worker.execute( this );

    }//END of START CLIENT


    /**
     * it is control the thread which are updating a GUI component
     * and with a loop it can control every server's message send to it
     */
    public void run(){

        //GUI component, now the view of the message is only CLI
        //take the messages send by server and print it
        while (true){

            if(input.hasNextLine()) {

                processMessage( input.nextLine() );

                if ( string.equals("quit") ){

                    closeConnection();
                    break;
                }
            }
        }

    }//END of RUN


    /**
     * in this method there is the process which analyses the message received
     * @param message the server's message
     */
    public void processMessage(String message){

        if ( message.equals("It's your turn, so you can write your move!") ){
            //if it is my turn i can write something
            System.out.println("It's your turn! You can write your move!!\n");
            string = scanner.nextLine();
            output.println( string );
            output.flush();

        }
        else if ( message.equals("You are the " + 1 + " player!") ) {
            System.out.println("You are the first player\n");
            //set the player's position
            playerNumber = 1;
        }
        else if ( message.equals("You are the " + 2 + " player!") ) {
            System.out.println("You are the second player\n");
            //set the player's position
            playerNumber = 2;
        }
        else if ( message.equals("You are the " + 3 + " player!") ) {
            System.out.println("You are the third player\n");
            //set the player's position
            playerNumber = 3;
        }
        else {
            System.out.println( message + "\n");
        }

    }//END of PROCESS MESSAGE


    /**
     * this method close the connection with the server
     * and the input, output stream
     */
    public void closeConnection () {

        input.close();
        output.close();
        try {
            connection.close();
        }catch (IOException e) {

            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("The connection with the server is closed!\n");

    }//END of CLOSE CONNECTION


    public static void main (String[] args){

        ClientSocket application;

        if (args.length == 0){
            //set the client: localhost
            application = new ClientSocket("127.0.0.1");
        }
        else{
            //set the client: other host
            application = new ClientSocket( args[0] );
        }
        //this is for the GUI: application.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }
}
