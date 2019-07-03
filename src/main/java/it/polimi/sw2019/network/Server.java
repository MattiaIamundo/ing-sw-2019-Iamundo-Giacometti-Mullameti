package it.polimi.sw2019.network;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.network.Socket.ServerSocket;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable{


    private ServerSocket serverSocket;
    //the controller
    private Game controller;
    //logger
    private static final Logger logger = Logger.getLogger( Server.class.getName() );

    public Server() {
        controller = new Game();
        serverSocket = new ServerSocket(this.controller);
        run();
    }

    public void run() {
        logger.log(Level.INFO, "{Server} has started serverSocket\n");
        serverSocket.run();

    }

    public static void main(String[] args) {
        new Server();
    }
}
