package it.polimi.sw2019.nethandler;

import it.polimi.sw2019.events.client_event.Cevent.Color;
import it.polimi.sw2019.events.client_event.Cevent.Login;
import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.nethandler.ContSelectInt;
import it.polimi.sw2019.view.PlayerView;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ContSelect implements ContSelectInt {

    private Socket socket;
    private PrintWriter out;
    private Scanner in;
    private boolean boo;
    private String string;

    /**
     * this is the constructor
     * @param socket1 the connection with the server
     */
    public ContSelect (Socket socket1) {
        socket = socket1;
        try{
            out = new PrintWriter(socket.getOutputStream());
            in =  new Scanner(socket.getInputStream());

        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    public void waitForNicknameRequest(PlayerView playerView) {

        List<String> list = new ArrayList<> (5);

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
    }

    public void waitingForColorRequest(PlayerView playerView) {

        List<String> colorlist = new ArrayList<> (5);
        boolean bii;

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

    }

    public void waitingForMap(PlayerView playerView) {
        boolean map;
        while ( !in.hasNextBoolean() ) {}

        map = in.nextBoolean();
        in.nextLine();

        playerView.requestMap(map);
    }

    public void waitingForSkull(PlayerView playerView) {

        System.out.println("ok");
        boolean skull;
        while ( !in.hasNextBoolean() ) {}


        skull = in.nextBoolean();
        in.nextLine();
        string = in.nextLine();

        playerView.requestSkull(skull);



    }

    public void waitForPing(PlayerView playerView) {
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
    }

    public boolean waitForOk() {
        string = in.nextLine();
        while ( string.equals("") ) {
            string = in.nextLine();
        }
        if ( string.equals("out") ) {
            throw new NoSuchElementException();
        }

        return string.equals("ok");
    }

    public boolean waitForAmIFirstPlayer() {

        while ( !in.hasNextLine() ) {}

        string = in.nextLine();

        while ( string.equals("") ) {
            string = in.nextLine();
        }
        if (string.equals("first")  ) {
            return false;
        }
        return true;
    }

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
}
