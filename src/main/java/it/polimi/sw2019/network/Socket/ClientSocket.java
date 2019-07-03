package it.polimi.sw2019.network.Socket;



import it.polimi.sw2019.events.server_event.VCevent.VCColor;
import it.polimi.sw2019.events.server_event.VCevent.VCLogin;
import it.polimi.sw2019.nethandler.ContSelect;
import it.polimi.sw2019.nethandler.ModViewEvent;
import it.polimi.sw2019.nethandler.ViewContEvent;
import it.polimi.sw2019.view.*;
import it.polimi.sw2019.view.ControllerClasses.TableController;
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
    private static final int portNumber = 12345;
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
    private List<String> colorList = new ArrayList<>(5);
    private TableController tableController;
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

    private boolean firstTime = true;
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


            //set the view
            playerView = new PlayerView(uIinterface, this);
            tableView = new TableView(uIinterface);
            weaponView = new WeaponView(uIinterface);
            ammoView = new AmmoView(uIinterface);
            powerUpView = new PowerUpView(uIinterface);


            contSelect = new ContSelect( connection, playerView);
            modViewEvent = new ModViewEvent( connection );
            viewContEvent = new ViewContEvent( connection );


        }catch(IOException e){
            logger.log(Level.SEVERE, e.toString(), e);
        }


    }//END of START CLIENT

    public ContSelect getContSelect(){
        return contSelect;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public TableView getTableView() {
        return this.tableView;
    }

    public void setUI(UIinterface uIinterface){
        this.uIinterface=uIinterface;
    }

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
            case "Ping": uIinterface.requestLobby("ok");
                break;
            case "Start": uIinterface.requestTable("ok");
                break;
            case "Refresh": uIinterface.requestRefresh("ok");
            default:
                break;
        }

    }

    public ViewContEvent getViewContEvent() {
        return this.viewContEvent;
    }


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

        Application.launch(GUI.class);//(MERITA)

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

    public TableController getTableController() {
        return this.tableController;
    }

    public void setTableController(TableController tableController) {
        this.tableController = tableController;
    }
}
