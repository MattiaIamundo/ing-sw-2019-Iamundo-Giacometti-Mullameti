package it.polimi.sw2019.network;

import it.polimi.sw2019.model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.sw2019.network.Socket.ServerSocket.*;

public class SocketLink implements Runnable{

    private ServerSocket serverlink;
    private static final Logger logger = Logger.getLogger( SocketLink.class.getName() );

    /**
     * this is the constructor
     * @param server the server
     */
    public SocketLink (ServerSocket server) {
        this.serverlink = server;
    }

    /**
     * start to accept a socket client to the server
     */
    private void addClient () {
        int i = 1;
        int numberThread = 5;

        while ( getPlayers().size() < 5 && !getOut() ) {

            try {
                //add a player
                PlayerThread p = new PlayerThread( this.serverlink.accept(), i, numberThread);
                if ( getPlayers().size() < 5 ) {
                    getPlayers().add( p );

                    if ( !getOut() ) {
                        //thread is set: runGame.execute
                        getRunGame().execute( getPlayers().get(i-1) );
                    }
                    else {
                        //if the timer set out: but the linker accept a player
                        //remove it
                        getPlayers().remove( getPlayers().size()-1 );
                        break;
                    }

                }

                System.out.println("The #" + i + " player is set\n");
                logger.log( Level.FINE, "process { ServerSocket } has set a player");
                i++;

            } catch (IOException e) {
                logger.log(Level.SEVERE, e.toString(), e);
                e.printStackTrace();
            }
        }
    }

    public void run() {
        addClient();
    }

}