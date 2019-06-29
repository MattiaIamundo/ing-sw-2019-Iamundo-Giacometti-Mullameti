package it.polimi.sw2019.network.Socket;



import it.polimi.sw2019.events.server_event.VCevent.VCColor;
import it.polimi.sw2019.events.server_event.VCevent.VCLogin;
import it.polimi.sw2019.nethandler.ContSelect;
import it.polimi.sw2019.nethandler.ModViewEvent;
import it.polimi.sw2019.nethandler.ViewContEvent;
import it.polimi.sw2019.view.*;
import javafx.application.Application;



import java.io.IOException;
import java.io.PrintWriter;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocket  {

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
    private UIinterface uIinterface;
    private String nickname;
    private List<String>colorList=new ArrayList<>(5);
    //the logger for debugging
    private static final Logger logger = Logger.getLogger( ClientSocket.class.getName() );

    //view
    private PlayerView playerView;
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
    private void startClient(){

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
            playerView = new PlayerView(uIinterface, this);
            tableView = new TableView(uIinterface);
            weaponView = new WeaponView(uIinterface);
            ammoView = new AmmoView(uIinterface);
            powerUpView = new PowerUpView(uIinterface);

        }catch(IOException e){
            logger.log(Level.SEVERE, e.toString(), e);
        }
        //it creates and starts the thread for this client

        //client is executed
        //worker.execute( this );

    }//END of START CLIENT

    /**
     * it is control the thread which are updating a GUI component
     * and with a loop it can control every server's message send to it
     */

    public ContSelect getContSelect(){
        return contSelect;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public void setUI(UIinterface uIinterface){
        this.uIinterface=uIinterface;
    }

    /*public void setNickname(String string){
        VCLogin vcLogin = new VCLogin(string);
        playerView.sendNickname(viewContEvent, vcLogin);
    }

    public void setColor(String string){
        System.out.println("setColor");
        VCColor vcColor = new VCColor(string);
        playerView.sendColor(viewContEvent, vcColor);
    }*/

    public void setInfo(String string , String info) {
        if (string.equals("nickname")) {

            VCLogin vcLogin = new VCLogin(info);
            playerView.sendNickname(viewContEvent, vcLogin);

        } else if (string.equals("character")) {

            VCColor vcColor = new VCColor(info);
            playerView.sendColor(viewContEvent, vcColor);

        }else if (string.equals("skull")) {

            playerView.sendSkull(viewContEvent, info);

        }else if (string.equals("map")) {

            playerView.sendMap(viewContEvent, info);

        }
    }
    public void notifyGUI(String string){
        switch(string){
            case "RequestNickname" : uIinterface.requestNickname("ok");
                break;
            case "RequestCharacter": uIinterface.requestColor("ok");
                break;
            case "RequestSkullNr" : uIinterface.requestSkull("ok");
                break;
            case "RequestMapType":uIinterface.requestMap("ok");
                break;
            case "Ping":
                uIinterface.requestLobby("ok");
                playerView.sendPong(this.viewContEvent);
                break;

        }

    }




    /*public void run(){
        ok = false;
        //welcome, set the nickname
        try{
            logger.log(Level.INFO, "{ClientSocket} nickname selection ");
            contSelect.waitForNicknameRequest(this.playerView);

            while ( !ok ) {

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
            do {
                ok = contSelect.waitForOk();
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

                ok = contSelect.waitForOk();
                if( !ok  ) {
                    contSelect.waitingForColorRequest(this.playerView);
                }
            }

            logger.log(Level.INFO, "{ClientSocket} first player selection");
            //to see if i'm the first player
            ok = contSelect.waitForAmIFirstPlayer();

            if (!ok) {

                ok = contSelect.waitForThereAreSkull();

                if (!ok) {

                    while ( !ok ) {
                        logger.log(Level.INFO, "{ClientSocket} skull selection");
                        contSelect.waitingForSkull(this.playerView);
                        ok = contSelect.waitForOk();

                    }
                }

                ok = contSelect.waitForThereIsMap();

                if ( !ok ) {

                    while ( !ok ) {

                        logger.log(Level.INFO, "{ClientSocket} map selection");
                        contSelect.waitingForMap(this.playerView);
                        ok = contSelect.waitForOk();
                    }
                }

            }

            ok = false;

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


        } catch (NoSuchElementException e) {
            input.close();
            output.close();
            logger.log( Level.INFO, "The connection with the server is closed: the game is already started!\n");
            worker.shutdown();
        }
        finally {

            closeConnection();
        }

    }//END of RUN */

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
            input.close();
            output.close();

            logger.log( Level.SEVERE, e.toString(), e);
            worker.shutdown();
        }
        logger.log( Level.INFO, "The connection with the server is closed!\n");
        worker.shutdown();

    }//END of CLOSE CONNECTION

    public void setView(UIinterface view){
        this.uIinterface=view;
    }//(MERITA)

    public static void main (String[] args){

        ClientSocket application;

        Application.launch(GUI.class);//(MERITA)

        //application = new ClientSocket("127.0.0.1");
    }

    public Boolean getOk() {
        return contSelect.waitForOk();
    }
    public Boolean getYouAreFirstPlayer(){return contSelect.waitForAmIFirstPlayer();}

    public void setColorList(List<String> colorList){

        this.colorList=colorList;


    }
    public List<String> getColorList(){
        System.out.println("colorget");
        return this.colorList;
    }

}
