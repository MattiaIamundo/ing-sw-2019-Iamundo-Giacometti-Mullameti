package it.polimi.sw2019.view;

import it.polimi.sw2019.events.client_event.Cevent.Login;
import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.nethandler.ContSelectInt;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

    public boolean waitForNicknameRequest(PlayerView playerView) {

        List<String> list = new ArrayList<> (5);

        boo = in.nextBoolean();
        //System.out.println(boo);
        string = in.nextLine();
        //System.out.println(string);
        string = in.nextLine();
        // System.out.println(string);
        //string = in.nextLine();
        //System.out.println(string);

        if( !string.equals("name?") ) {

            if (string.equals("Reconnection")) {
                Reconnection re = new Reconnection(boo,string);
                playerView.requestNickname(re);
            }
            else if ( string.equals("ok") ) {
                playerView.sendOk();
                return true;
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
        return false;
    }

    public boolean waitForPing(PlayerView playerView) {
        string = in.nextLine();
        if (string.equals("Ping")) {
            playerView.waitForPing();
            return false;
        }
        return true;
    }

}
