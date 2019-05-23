package it.polimi.sw2019.view;

import com.google.gson.Gson;
import it.polimi.sw2019.controller.Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PlayerRemoteView extends PlayerView {

    private static Socket socket;

    public PlayerRemoteView (Socket socket1) {
        socket = socket1;
    }


    public static void nickname(Socket socket) {

        try{
            PrintWriter output = new PrintWriter( socket.getOutputStream());
            output.println("nickname\n");
            output.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void nicknameReturnn ( Socket socket) {

        try{
            Scanner input = new Scanner( socket.getInputStream());

            if (input.hasNextLine() ) {

                Gson g = new Gson();
                String clientName = g.fromJson( input.nextLine() , String.class);
                //controller.nicknameReturn(clientName);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void timer () {

        try{
            PrintWriter output = new PrintWriter( socket.getOutputStream());
            output.println("timer\n");
            output.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTimer () {
        try{
            Scanner input = new Scanner( socket.getInputStream());

            if (input.hasNextInt() ) {

                Game.addTimer( input.nextInt() );
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void showPlayer() {

    }
}
