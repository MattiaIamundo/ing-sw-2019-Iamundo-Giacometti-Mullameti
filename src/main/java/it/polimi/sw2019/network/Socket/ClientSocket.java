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

public class ClientSocket extends JFrame implements Runnable {

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
    //boolean to set the condition to go out the loops
    private boolean ok = false;
    //boolean to set that the game is finished
    private boolean gameover = false;
    //the GUI or CLI
    private UIinterface userImp;
    //the logger for debugging
    private static final Logger logger = Logger.getLogger( ClientSocket.class.getName() );

    //view
    private transient PlayerView playerView;
    private transient TableView tableView;
    private transient WeaponView weaponView;
    private transient AmmoView ammoView;
    private transient PowerUpView powerUpView;

    //the NH
    private transient ContSelect contSelect;
    private transient ModViewEvent modViewEvent;
    private transient ViewContEvent viewContEvent;

    private transient ExecutorService worker = Executors.newFixedThreadPool( 1 );

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
            logger.log(Level.INFO, "{ClientSocket} nickname selection ");
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

            logger.log(Level.INFO, "{ClientSocket} color selection");
            ok = false;

            //select the color
            contSelect.waitingForColorRequest(this.playerView);
            while (!ok) {

                ok = contSelect.waitForOk(this.playerView);
                if( !ok  ) {
                    contSelect.waitingForColorRequest(this.playerView);
                }
            }

            logger.log(Level.INFO, "{ClientSocket} first player selection");

            ok = contSelect.waitForAmIFirstPlayer(this.playerView);

            //to see if i'm the first player
            while ( !ok ) {


                logger.log(Level.INFO, "{ClientSocket} skull selection");
                contSelect.waitingForSkull(this.playerView);
                ok = contSelect.waitForOk(this.playerView);


            }

            ok = false;

            //devo implementare come fare per la mappa//

            logger.log(Level.INFO, "{ClientSocket} ping pong action");
            //ping to pong
            while ( !ok ) {

                ok = contSelect.waitForPing(this.playerView);
                playerView.sendPing(viewContEvent);

            }

            logger.log(Level.INFO, "{ClientSocket} the game is starting");

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
            worker.shutdown();
        }
        logger.log( Level.INFO, "The connection with the server is closed!\n");
        worker.shutdown();

    }//END of CLOSE CONNECTION

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
