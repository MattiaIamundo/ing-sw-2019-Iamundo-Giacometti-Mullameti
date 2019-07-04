package it.polimi.sw2019.nethandler;

import it.polimi.sw2019.controller.Action;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.events.server_event.VCevent.VCColor;
import it.polimi.sw2019.events.server_event.VCevent.VCLogin;
import it.polimi.sw2019.nethandler.ViewContEventInt;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ViewContEvent implements ViewContEventInt {

    private Socket socket;
    private PrintWriter output;
    private ObjectOutputStream objectOutputStream;
    private static final Logger logger = Logger.getLogger( ViewContEvent.class.getName() );

    public ViewContEvent (Socket socket) {

        this.socket = socket;
        try {
            output = new PrintWriter(socket.getOutputStream());
            //objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        }catch (IOException e) {
            logger.log(Level.SEVERE, e.toString(), e.getMessage());
        }
    }

    public void sendSkull(String st) {
        /*
        try{
            objectOutputStream.writeUTF(st);
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }

         */
        output.println(st);
        output.flush();
    }

    public void sendMap(String map) {

        output.println(map);
        output.flush();
    }

    public void sendColor(VCColor vcColor) {

        System.out.println("SendColorVce");
        output.println(vcColor.getColor() );
        output.flush();
    }

    public void sendNickname( VCLogin nick ) {
/*
        try{
            objectOutputStream.writeUTF(nick.getNickname());
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }



 */
        output.println( nick.getNickname() );
        output.flush();
    }

    public void sendNickname(Reconnection re) {
        output.println( re.getRecon() );
        output.flush();
    }

    public void sendPong() {
        output.println( "Pong" );
        output.flush();

    }

    public void setObjectOutputStream() {
        try{
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException ex) {
            //
        }
    }

    public void sendActionEv(ActionEv actionEv) {
        try{
            objectOutputStream.writeObject(actionEv);
            objectOutputStream.flush();
        } catch (IOException ex) {
            //
        }



    }
}
