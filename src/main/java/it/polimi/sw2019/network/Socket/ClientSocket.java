package it.polimi.sw2019.network.Socket;

import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.nethandler.ContSelect;
import it.polimi.sw2019.nethandler.ModViewEvent;
import it.polimi.sw2019.nethandler.ViewContEvent;
import it.polimi.sw2019.view.*;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocket extends JFrame implements Runnable, Serializable{

    //the port number
    private final int portNumber = 12345;
    //connection to the server
    private Socket connection;
    //input from the server
    private Scanner input;
    //output for the server
    private PrintWriter output;
    //host name for the server
    private String serverHost;
    //it contains the what the client is writing
    private String string = "nope";
    //
    private boolean ok = false;
    //
    private boolean gameover = false;
    //the GUI or CLI
    private UIinterface userImp;
    //the logger for debugging
    private static final Logger logger = Logger.getLogger( ClientSocket.class.getName() );

    //view
    private PlayerView playerView;
    private TableView tableView;
    private WeaponView weaponView;
    private AmmoView ammoView;
    private PowerUpView powerUpView;

    //the NH
    private ContSelect contSelect;
    private ModViewEvent modViewEvent;
    private ViewContEvent viewContEvent;

    private ExecutorService worker = Executors.newFixedThreadPool( 1 );

    /**
     * this is the constructor
     * @param host the server name
     */
    public ClientSocket(String host, boolean setTheCLI){
        //set the server name
        serverHost = host;
        //commands for set the GUI
        startClient(setTheCLI);

    }//END of the CONSTRUCTOR

    /**
     * in this method are set the connection with the server,
     * get the streams and run the thread's client
     */
    private void startClient(boolean cli){

        try{
            //set the connection
            connection = new Socket( InetAddress.getByName(serverHost), portNumber);
            //getting the input and output stream
            input = new Scanner( connection.getInputStream() );
            output = new PrintWriter( connection.getOutputStream() );

            //set the socket to the NH
            contSelect = new ContSelect( connection );
            modViewEvent = new ModViewEvent( connection );
            viewContEvent = new ViewContEvent( connection );

            //set the view
            playerView = new PlayerView(null);
            tableView = new TableView(null);
            weaponView = new WeaponView(null);
            ammoView = new AmmoView( null);
            powerUpView = new PowerUpView(null);

            if (cli) {
                userImp = new CLI(playerView,viewContEvent);
            }
            else {
                userImp = new GUI();
            }

            //set the view
            playerView = new PlayerView(userImp);
            tableView = new TableView(userImp);
            weaponView = new WeaponView(userImp);
            ammoView = new AmmoView(userImp);
            powerUpView = new PowerUpView(userImp);

        }catch(IOException e){
            logger.log(Level.SEVERE, e.toString(), e);
        }
        //it creates and starts the thread for this client

        //client is executed
        worker.execute( this );

    }//END of START CLIENT

    /**
     * it is control the thread which are updating a GUI component
     * and with a loop it can control every server's message send to it
     */
    public void run(){
        ok = false;
        //welcome, set the nickname
        try{
            contSelect.waitForNicknameRequest(this.playerView);

 /*           while ( !ok ) {

                if (string.equals("quit")) {

                    Reconnection rec = new Reconnection(true, string);
                    playerView.sendNickname(viewContEvent, rec);
                    closeConnection();
                    return;

                } else if (string.equals("Reconnection")) {

                    System.out.println("Please write your nickname to reconnect to the game!\n");
                    string = scanner.nextLine();
                    Reconnection rec = new Reconnection(true, string);
                    playerView.sendNickname(viewContEvent, rec);

                }
            }

  */        do {
                ok = contSelect.waitForOk(this.playerView);
                if ( !ok ) {
                    contSelect.waitForNicknameRequest(this.playerView);
                }
            }
            while ( !ok );



            System.out.println("Please for others!\n");
            ok = false;
            //select the color
            contSelect.waitingForColorRequest(this.playerView);
            while (!ok) {

                ok = contSelect.waitForOk(this.playerView);
                if( !ok  ) {
                    contSelect.waitingForColorRequest(this.playerView);;
                }
            }


            System.out.println("Please waiting for others playersssssssss!\n");
            ok = false;

            //to see if i'm the first player
   /*         while (!ok) {

            }

    */

            System.out.println("Please waiting for others players!\n");

            //ping to pong
            while ( !ok ) {

                ok = contSelect.waitForPing(this.playerView);
                playerView.sendPing(viewContEvent);
//i'm here
            }

            System.out.println("The game is starting!\n");

            while ( !gameover ) {

                //game is started!
                while ( !ok ) {

                    //ok = contSelect.waitForMyTurn(this.playerView);
                    playerView.sendPing(viewContEvent);

                }


            }


        }finally {

            closeConnection();
        }

    }//END of RUN


    /**
     * this method close the connection with the server
     * and the input, output stream
     */
    private void closeConnection () {

        input.close();
        output.close();
        try {
            connection.close();
        }catch ( IOException | NoSuchElementException | IllegalStateException e) {

            logger.log( Level.SEVERE, e.toString(), e);
            System.exit(1);
            worker.shutdown();
        }
        System.out.println("The connection with the server is closed!\n");
        worker.shutdown();

    }//END of CLOSE CONNECTION

    public void setGameover(boolean status) {
        this.gameover = status;
    }

    public void receiveEvent() {}

    public static void main (String[] args){

        ClientSocket application;

        if (args.length == 0){
            //set the client: localhost
            application = new ClientSocket("127.0.0.1", true);
        }
        else{
            //set the client: other host
            application = new ClientSocket( args[0], true );
        }

    }
}
