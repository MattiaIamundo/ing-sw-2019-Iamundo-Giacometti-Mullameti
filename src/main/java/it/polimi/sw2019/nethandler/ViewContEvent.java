package it.polimi.sw2019.nethandler;

import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.events.server_event.VCevent.VCColor;
import it.polimi.sw2019.events.server_event.VCevent.VCLogin;
import it.polimi.sw2019.nethandler.ViewContEventInt;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class ViewContEvent implements ViewContEventInt {

    private Socket socket;
    private PrintWriter output;
    private Scanner in;

    public ViewContEvent (Socket socket) {

        this.socket = socket;
        try {
            output = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendColor(VCColor vcColor) {

        output.println(vcColor.getColor() );
        output.flush();
    }

    public void sendNickname( VCLogin nick ) {

        output.println( nick.getNickname() );
        output.flush();
    }

    public void sendNickname(Reconnection re) {
        output.println( re.getRecon() );
        output.flush();
    }

    public void sendPing() {
        output.println( "Ping" );
        output.flush();
    }
}
