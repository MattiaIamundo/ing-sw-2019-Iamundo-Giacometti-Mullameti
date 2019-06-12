package it.polimi.sw2019.view;

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

                output.println( "\n" );
                output.flush();
            }

    }

    public String waitForNickname() {

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

    public void requestNickname(Reconnection reconnection) {
        output.println(reconnection.getFirstTime());
        output.flush();

        output.println(reconnection.getRecon());
        output.flush();
    }

    public void sendGoodbye() {
        output.print("The nickname was not found, so goodbye!");
        output.flush();
    }

    public void sendOk() {

        output.println(false);
        output.flush();
        output.println("ok");
        output.flush();
    }

    protected void showPlayer() {

    }
}
