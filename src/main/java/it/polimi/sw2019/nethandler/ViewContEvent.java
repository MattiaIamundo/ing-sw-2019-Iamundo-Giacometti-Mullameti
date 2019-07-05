package it.polimi.sw2019.nethandler;

import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.events.server_event.VCevent.VCColor;
import it.polimi.sw2019.events.server_event.VCevent.VCLogin;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ViewContEvent implements ViewContEventInt {

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private static final Logger logger = Logger.getLogger( ViewContEvent.class.getName() );

    public ViewContEvent (Socket socket) {

        this.socket = socket;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        }catch (IOException e) {
            logger.log(Level.SEVERE, e.toString(), e.getMessage());
        }
    }

    public void sendSkull(String st) {

        try{
            objectOutputStream.writeUTF(st);
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.toString(), ex.getMessage());
        }

    }

    public void sendMap(String map) {

        try{
            objectOutputStream.writeUTF(map);
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.toString(), ex.getMessage());
        }
    }

    public void sendColor(VCColor vcColor) {

        try{
            objectOutputStream.writeUTF(vcColor.getColor());
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.toString(), ex.getMessage());
        }
    }

    public void sendNickname( VCLogin nick ) {

        try{
            objectOutputStream.writeUTF(nick.getNickname());
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.toString(), ex.getMessage());
        }

    }

    public void sendNickname(Reconnection re) {

        try{
            objectOutputStream.writeUTF(re.getRecon());
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.toString(), ex.getMessage());
        }
    }

    public void sendPong() {

        try{
            objectOutputStream.writeUTF( "Pong" );
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.toString(), ex.getMessage());
        }
    }

    public void sendActionEv(ActionEv actionEv) {
        try{
            objectOutputStream.writeObject(actionEv);
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.toString(), ex.getMessage());
        }

    }
}
