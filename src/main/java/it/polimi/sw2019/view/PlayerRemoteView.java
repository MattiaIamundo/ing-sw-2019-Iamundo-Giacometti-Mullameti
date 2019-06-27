package it.polimi.sw2019.view;

import com.google.gson.Gson;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.client_event.Cevent.Color;
import it.polimi.sw2019.events.client_event.Cevent.Login;
import it.polimi.sw2019.events.client_event.Cevent.Reconnection;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class PlayerRemoteView extends PlayerView {

    private Socket socket;
    private String set = "pippo";
    private PrintWriter output;
    private Scanner input;
    private Gson gson;

    public PlayerRemoteView (Socket socket1) {
        socket = socket1;
        try{
            output = new PrintWriter(socket.getOutputStream());
            input = new Scanner(socket.getInputStream());

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void requestNickname(Login login) {

        //i have to send to the client this request
            output.println( login.isFirstTime() );
            output.flush();

            if( login.getNickname().isEmpty() ) {

                output.println("name?");
                output.flush();

            }
            else {

                for( String s : login.getNickname() ) {

                    output.println( s );
                    output.flush();
                }

                output.println( "" );
                output.flush();
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

        output.println("Ping");
        output.flush();
    }

    public String waitForPong() {
        set = input.nextLine();
        return set;
    }

    public void requestSkull(boolean firstTime) {
        output.println(firstTime);
        output.flush();

        output.println("skull");
        output.flush();
    }

    public void requestMap(boolean firstTime) {
        output.println(firstTime);
        output.flush();
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
        output.println(reconnection.getFirstTime());
        output.flush();

        output.println(reconnection.getRecon());
        output.flush();
    }

    public void requestColor(Color color) {

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
    }

    public void sendGoodbye() {
        output.println("The nickname was not found, so goodbye!");
        output.flush();
    }

    public void sendOut() {
        output.println("out");
        output.flush();
    }

    public void sendOk() {

        output.println("ok");
        output.flush();
    }

    public void sendNotOk() {
        output.println("notok");
        output.flush();
    }

    public void sendYouAreFirstPlayer() {
        output.println("first");
        output.flush();
    }

    public void sendYouAreNotFirstPlayer() {
        output.println("oneother");
        output.flush();
    }

    public void sendThereAreSkull() {
        output.println("true");
        output.flush();
    }

    public void sendThereAreNotSkull() {
        output.println("false");
        output.flush();
    }

    public void sendThereIsMap() {
        output.println("true");
        output.flush();
    }

    public void sendThereIsNotMap() {
        output.println("false");
        output.flush();
    }

    protected void showPlayer() {

    }

    public ActionEv waitForAction() {
        //  i have to wait for a ActionEv
        set = input.nextLine();
        //  tramite Gson devo deserializzare l evento e castarlo con ActionEv
        //  e chiamare la giusta procedura per svolegere l evento
        return gson.fromJson(set, ActionEv.class);
    }
}
