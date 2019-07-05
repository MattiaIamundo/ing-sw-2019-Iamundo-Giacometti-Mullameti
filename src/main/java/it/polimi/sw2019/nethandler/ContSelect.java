package it.polimi.sw2019.nethandler;

import it.polimi.sw2019.events.NotifyClient;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.Cevent.Color;
import it.polimi.sw2019.events.client_event.Cevent.Login;
import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.events.client_event.Cevent.StartGameEv;
import it.polimi.sw2019.view.PlayerView;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContSelect implements ContSelectInt{

    private Socket socket;
    private PrintWriter out;
    private Scanner in;
    private ObjectInputStream objectInputStream;
    private boolean boo;
    private String string;
    private PlayerView playerView;
    private static final Logger logger = Logger.getLogger( ContSelect.class.getName() );

    /**
     * this is the constructor
     * @param socket1 the connection with the server
     */
    public ContSelect (Socket socket1, PlayerView playerView) {
        socket = socket1;
        this.playerView = playerView;
        try{
            //out = new PrintWriter(socket.getOutputStream());
            //in =  new Scanner(socket.getInputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

        } catch ( IOException e) {
            logger.log(Level.SEVERE, e.toString(), e.getMessage());
        }
    }

    public void waitForNicknameRequest(PlayerView playerView) {

        List<String> list = new ArrayList<> (5);
        try {
            boo = objectInputStream.readBoolean();
            string = objectInputStream.readUTF();
            //System.out.println(string);
            if( !string.equals("name?") ) {

                if (string.equals("Reconnection")) {
                    Reconnection re = new Reconnection(boo,string);
                    playerView.requestNickname(re);
                }
                else {
                    while (!string.equals("")) {

                        list.add(string);
                        string = objectInputStream.readUTF();
                    }

                    Login login = new Login(boo, list);
                    playerView.requestNickname(login);
                }

            }
            else if ( string.equals("name?")) {
                Login login = new Login(boo, list);
                playerView.requestNickname(login);
            }
        } catch (IOException ex) {
            //
        }
/*
        while ( !in.hasNextBoolean() ) {
            in.nextLine();
        }
        boo = in.nextBoolean();
        //System.out.println(boo);
        string = in.nextLine();
        //System.out.println(string);
        string = in.nextLine();
        //System.out.println(string);
        //in.nextLine();
        //System.out.println(string);

        if( !string.equals("name?") ) {

            if (string.equals("Reconnection")) {
                Reconnection re = new Reconnection(boo,string);
                playerView.requestNickname(re);
            }
            else {
                while (!string.equals("")) {

                    list.add(string);
                    string = in.nextLine();
                }

                Login login = new Login(boo, list);
                playerView.requestNickname(login);
            }

        }
        else if ( string.equals("name?")) {
            Login login = new Login(boo, list);
            playerView.requestNickname(login);
        }

 */
    }

    public void waitingForColorRequest(PlayerView playerView) {

        List<String> colorlist = new ArrayList<> (5);
        boolean bii;
        try{
            boo = objectInputStream.readBoolean();
            bii = objectInputStream.readBoolean();
            string = objectInputStream.readUTF();

            if (!string.equals("ok")) {

                while (!string.equals("")) {

                    colorlist.add(string);
                    string = objectInputStream.readUTF();
                }

                Color color = new Color(boo, bii, colorlist);
                playerView.requestColor(color);

            }
        } catch (IOException ex) {
            //
        }
        /*
        while ( !in.hasNextBoolean() ) {
            in.nextLine();
        }

        boo = in.nextBoolean();
        //System.out.println(boo);
        string = in.nextLine();
        //System.out.println(string);
        bii = in.nextBoolean();
        //System.out.println(bii);
        string = in.nextLine();
        //System.out.println(string);
        string = in.nextLine();
        //System.out.println(string);

        if (!string.equals("ok")) {

            while (!string.equals("")) {

                colorlist.add(string);
                string = in.nextLine();
            }

            Color color = new Color(boo, bii, colorlist);
            playerView.requestColor(color);

        }

         */

    }

    public void waitingForMap(PlayerView playerView) {

        boolean map;
        try{
            map = objectInputStream.readBoolean();
            playerView.requestMap(map);
        } catch (IOException ex) {
            //
        }
        /*

        while ( !in.hasNextBoolean() ) {}

        map = in.nextBoolean();
        in.nextLine();

        playerView.requestMap(map);

         */
    }

    public void waitingForSkull(PlayerView playerView) {
        System.out.println("ok");
        boolean skull;
        try{
            skull = objectInputStream.readBoolean();
            string = objectInputStream.readUTF();

            playerView.requestSkull(skull);

        } catch (IOException ex) {
            //
        }

/*
        while ( !in.hasNextBoolean() ) {}


        skull = in.nextBoolean();
        in.nextLine();
        string = in.nextLine();

        playerView.requestSkull(skull);


 */


    }

    public void waitForPing(PlayerView playerView) {
        try{
            string = objectInputStream.readUTF();
            if (string.equals("Ping")) {
                playerView.waitForPing();
            }
            else if ( string.equals("out") ) {
                throw new NoSuchElementException();
            }
            else if (string.equals("start")) {
                playerView.waitForStart();
            }
        } catch (IOException ex) {
            //
        }
        /*
        string = in.nextLine();
        if (string.equals("Ping")) {
            playerView.waitForPing();
        }
        else if ( string.equals("out") ) {
            throw new NoSuchElementException();
        }
        else if (string.equals("start")) {
            playerView.waitForStart();
        }

         */
    }

    public boolean waitForOk() {
        try{
            string = objectInputStream.readUTF();
            if (string.equals("out")) {
                throw new NoSuchElementException();
            }
            else if (string.equals("ok")) {
                return true;
            }

        } catch (IOException ex) {
            //
        }
        return false;

        /*
        string = in.nextLine();
        while ( string.equals("") ) {
            string = in.nextLine();
        }
        if ( string.equals("out") ) {
            throw new NoSuchElementException();
        }

        return string.equals("ok");

         */
    }

    public boolean waitForAmIFirstPlayer() {
        try {
            string = objectInputStream.readUTF();
            if (string.equals("first")) {
                return false;
            }
        } catch (IOException ex) {
            //
        }
        return true;
        /*
        while ( !in.hasNextLine() ) {}

        string = in.nextLine();

        while ( string.equals("") ) {
            string = in.nextLine();
        }
        if (string.equals("first")  ) {
            return false;
        }
        return true;

         */
    }
/*
    public boolean waitForThereAreSkull() {

        while ( !in.hasNextLine() ) {}

        string = in.nextLine();

        while ( string.equals("") ) {
            string = in.nextLine();
        }
        if (string.equals("true")  ) {
            return true;
        }
        return false;
    }

 */
/*
    public boolean waitForThereIsMap() {

        while ( !in.hasNextLine() ) {}

        string = in.nextLine();

        while ( string.equals("") ) {
            string = in.nextLine();
        }
        if (string.equals("true")  ) {
            return true;
        }
        return false;
    }

 */

    public NotifyReturn waitForStartGameEv() {
        StartGameEv notifyReturn = null;
        try{
            notifyReturn = (StartGameEv) objectInputStream.readObject();
            return notifyReturn;
        } catch (IOException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return notifyReturn;
    }

    public NotifyClient waitForNotifyReturnEvent () {
        NotifyClient notifyReturn = null;
        try {
            notifyReturn = (NotifyClient) objectInputStream.readObject();
            return notifyReturn;
        } catch (IOException | ClassNotFoundException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return notifyReturn;
    }


}
