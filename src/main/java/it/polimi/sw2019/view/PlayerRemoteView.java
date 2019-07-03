package it.polimi.sw2019.view;

import com.google.gson.Gson;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.Cevent.Color;
import it.polimi.sw2019.events.client_event.Cevent.Login;
import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.events.client_event.Cevent.StartGameEv;
import it.polimi.sw2019.network.Socket.ServerSocket;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerRemoteView extends Observable<ActionEv> implements Observer <NotifyReturn> {

    private Socket socket;
    private String set = "pippo";
    //private PrintWriter output;
    private Scanner input;
    private Gson gson;
    private ObjectOutputStream objectOutputStream;
    private static final Logger logger = Logger.getLogger( PlayerRemoteView.class.getName() );

    public PlayerRemoteView (Socket socket1) {
        socket = socket1;
        try{
            //output = new PrintWriter(socket.getOutputStream());
            input = new Scanner(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.toString(), ex.getMessage());
        }

    }

    public void requestNickname(Login login) {
        try{
            //i have to send to the client this request
            objectOutputStream.writeBoolean( login.isFirstTime() );
            objectOutputStream.flush();

            if( login.getNickname().isEmpty() ) {

                objectOutputStream.writeUTF("name?");
                objectOutputStream.flush();

            }
            else {

                for( String s : login.getNickname() ) {

                    objectOutputStream.writeUTF( s );
                    objectOutputStream.flush();
                }

                objectOutputStream.writeUTF( "" );
                objectOutputStream.flush();
            }
        } catch (IOException ex) {
            //
        }


    }

    public String waitForNickname() {

        set = input.nextLine();
        return set;
    }

    public String waitForColor() {

        set = input.nextLine();
        return set;
    }

    public void sendPing() {
        try {
            objectOutputStream.writeUTF("Ping");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println("Ping");
        //output.flush();
    }

    public String waitForPong() {
        set = input.nextLine();
        return set;
    }

    public void requestSkull(boolean firstTime) {
        try {
            objectOutputStream.writeBoolean(firstTime);
            objectOutputStream.flush();
            objectOutputStream.writeUTF("skull");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println(firstTime);
        //output.flush();

        //output.println("skull");
        //output.flush();
    }

    public void requestMap(boolean firstTime) {
        try {
            objectOutputStream.writeBoolean(firstTime);
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println(firstTime);
        //output.flush();
    }

    public String waitForSkull() {
        set = input.nextLine();
        return set;
    }

    public String waitForMap() {
        set = input.nextLine();
        return set;
    }

    public void requestNickname(Reconnection reconnection) {
        try {
            objectOutputStream.writeBoolean(reconnection.getFirstTime());
            objectOutputStream.flush();
            objectOutputStream.writeUTF(reconnection.getRecon());
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println(reconnection.getFirstTime());
        //output.flush();

        //output.println(reconnection.getRecon());
        //output.flush();
    }

    public void requestColor(Color color) {

        try {
            objectOutputStream.writeBoolean(color.isFirstTime());
            objectOutputStream.flush();
            objectOutputStream.writeBoolean(color.isDuplicated());
            objectOutputStream.flush();
            for( String s : color.getColors()) {

                objectOutputStream.writeUTF( s );
                objectOutputStream.flush();
            }

            objectOutputStream.writeUTF( "" );
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        /*
        output.println( color.isFirstTime() );
        output.flush();

        output.println( color.isDuplicated() );
        output.flush();

        for( String s : color.getColors()) {

            output.println( s );
            output.flush();
        }

        output.println( "" );
        output.flush();

         */
    }

    public void sendGoodbye() {
        try {
            objectOutputStream.writeUTF("The nickname was not found, so goodbye!");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println("The nickname was not found, so goodbye!");
        //output.flush();
    }

    public void sendOut() {
        try {
            objectOutputStream.writeUTF("out");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println("out");
        //output.flush();
    }

    public void sendOk() {

        try {
            objectOutputStream.writeUTF("ok");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println("ok");
        //output.flush();
    }

    public void sendNotOk() {
        try {
            objectOutputStream.writeUTF("notok");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println("notok");
        //output.flush();
    }

    public void sendYouAreFirstPlayer() {
        try {
            objectOutputStream.writeUTF("first");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println("first");
        //output.flush();
    }

    public void sendYouAreNotFirstPlayer() {
        try {
            objectOutputStream.writeUTF("oneother");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println("oneother");
        //output.flush();
    }

    public void sendThereAreSkull() {
        try {
            objectOutputStream.writeUTF("true");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println("true");
        //output.flush();
    }

    public void sendThereAreNotSkull() {
        try {
            objectOutputStream.writeUTF("false");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
       //output.println("false");
        //output.flush();
    }

    public void sendThereIsMap() {
        try {
            objectOutputStream.writeUTF("true");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println("true");
        //output.flush();
    }

    public void sendThereIsNotMap() {
        try {
            objectOutputStream.writeUTF("false");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println("false");
        //output.flush();
    }


    public ActionEv waitForAction() {
        //  i have to wait for a ActionEv
        set = input.nextLine();

        return gson.fromJson(set, ActionEv.class);
    }

    public void sendStartGame() {
        try {
            objectOutputStream.writeUTF("start");
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }
        //output.println("start");
        //output.flush();
    }

    public void sendAllModel(StartGameEv startGameEv) {

        try{
            objectOutputStream.writeObject(startGameEv);
            objectOutputStream.flush();
            objectOutputStream.reset();
        } catch (IOException ex) {
            //do nothing
        }


    }



    public void sendEvent(NotifyReturn notifyReturn) {
        set = gson.toJson(notifyReturn);
        //output.println(set);
        //output.flush();
    }


    public void notify(ActionEv actionEv) {

    }

    public void update(NotifyReturn notifyReturn) {

    }



}
