package it.polimi.sw2019.view;

import com.google.gson.Gson;
import it.polimi.sw2019.nethandler.ContSelectInt;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static it.polimi.sw2019.view.PlayerView.*;

public class ContSelect implements ContSelectInt {

    private static Socket socket;

    /**
     * this is the constructor
     * @param socket1 the connection with the server
     */
    public ContSelect (Socket socket1) {
        socket = socket1;
    }

    public void addPlayer() {

        nickname(socket);
    }

    public static void nicknameReturnn (String name, Socket socket) {


        PrintWriter output = null;
        try {
            output = new PrintWriter ( socket.getOutputStream() );
        }catch (IOException e) {
            e.printStackTrace();
        }

        output.println(name);
        output.flush();
    }

    public void setTimer() {
       timer();
    }

    public static  void addTimer(int time) {

        PrintWriter output = null;
        /*Gson g = new Gson();
        try {
            FileWriter writer = new FileWriter("File_Json/gson_files.json");
            g.toJson( time,  writer );
        }catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            output = new PrintWriter ( socket.getOutputStream() );
        }catch (IOException e) {
            e.printStackTrace();
        }

        output.println( time );
        output.flush();
    }

}
