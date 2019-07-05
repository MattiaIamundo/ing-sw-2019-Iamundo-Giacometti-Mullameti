package it.polimi.sw2019.view;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.Cevent.Color;
import it.polimi.sw2019.events.client_event.Cevent.Login;
import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.events.client_event.Cevent.StartGameEv;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerRemoteView extends Observable<ActionEv> implements Observer <NotifyReturn> {

    private Socket socket;
    private String set = "pippo";
    private Game controller;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private static final Logger logger = Logger.getLogger( PlayerRemoteView.class.getName() );

    public PlayerRemoteView (Socket socket1, Game controller) {
        socket = socket1;
        try{
            this.controller = controller;
            logger.log(Level.INFO, "{PlayerRemoteView} set the objectOutputStream");
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            logger.log(Level.INFO, "{PlayerRemoteView} set the objectInputStream");
            objectInputStream = new ObjectInputStream(socket.getInputStream());


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
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }


    }

    public String waitForNickname() {

        try {
            set = objectInputStream.readUTF();
            return set;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return set;
    }

    public String waitForColor() {

        try {
            set = objectInputStream.readUTF();
            return set;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return set;
    }

    public void sendPing() {
        try {
            objectOutputStream.writeUTF("Ping");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public String waitForPong() {

        try {
            set = objectInputStream.readUTF();
            return set;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return set;
    }

    public void requestSkull(boolean firstTime) {
        try {
            objectOutputStream.writeBoolean(firstTime);
            objectOutputStream.flush();
            objectOutputStream.writeUTF("skull");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void requestMap(boolean firstTime) {
        try {
            objectOutputStream.writeBoolean(firstTime);
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public String waitForSkull() {

        try {
            set = objectInputStream.readUTF();
            return set;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return set;
    }

    public String waitForMap() {

        try {
            set = objectInputStream.readUTF();
            return set;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return set;
    }

    public void requestNickname(Reconnection reconnection) {
        try {
            objectOutputStream.writeBoolean(reconnection.getFirstTime());
            objectOutputStream.flush();
            objectOutputStream.writeUTF(reconnection.getRecon());
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
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
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void sendGoodbye() {
        try {
            objectOutputStream.writeUTF("The nickname was not found, so goodbye!");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void sendOut() {
        try {
            objectOutputStream.writeUTF("out");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void sendOk() {

        try {
            objectOutputStream.writeUTF("ok");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void sendNotOk() {
        try {
            objectOutputStream.writeUTF("notok");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void sendYouAreFirstPlayer() {
        try {
            objectOutputStream.writeUTF("first");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void sendYouAreNotFirstPlayer() {
        try {
            objectOutputStream.writeUTF("oneother");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void sendThereAreSkull() {
        try {
            objectOutputStream.writeUTF("true");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void sendThereAreNotSkull() {
        try {
            objectOutputStream.writeUTF("false");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void sendThereIsMap() {
        try {
            objectOutputStream.writeUTF("true");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void sendThereIsNotMap() {
        try {
            objectOutputStream.writeUTF("false");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public void waitForAction() {

        try {
            ActionEv actionEv = (ActionEv) objectInputStream.readObject();
            notify(actionEv);
        } catch (IOException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }


    }

    public void sendStartGame() {
        try {
            objectOutputStream.writeUTF("start");
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void sendAllModel(StartGameEv startGameEv) {

        try{
            objectOutputStream.writeObject(startGameEv);
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }


    }



    public void sendEvent(NotifyReturn notifyReturn) {
        try {
            objectOutputStream.writeObject(notifyReturn);
            objectOutputStream.flush();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }


    public void update(NotifyReturn notifyReturn) {
        sendEvent(notifyReturn);
    }



}
