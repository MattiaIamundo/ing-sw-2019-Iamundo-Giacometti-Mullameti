package it.polimi.sw2019.network;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.controller.MultiGame;
import it.polimi.sw2019.network.RMI.ServerRMI;
import it.polimi.sw2019.network.Socket.ServerSocket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable{

    //the two "sub" server: RMI e Socket
    //private ServerRMI serverRMI;
    private ServerSocket serverSocket;
    //the controller
    private MultiGame multiGame;
    private Game controller;
    //logger
    private static final Logger logger = Logger.getLogger( Server.class.getName() );

    public Server() {
        multiGame = new MultiGame();
        controller = new Game();
        //serverRMI = new ServerRMI(this.controller);
        serverSocket = new ServerSocket(this.controller);
        run();
    }

    public void run() {

        //start the two sub server
        //serverRMI.startServerRMI();
        serverSocket.run();
        logger.log(Level.INFO, "{Server} has started serverRMI and serverSocket\n");

    }

    public static void main(String[] args) {
        new Server();
    }
}
