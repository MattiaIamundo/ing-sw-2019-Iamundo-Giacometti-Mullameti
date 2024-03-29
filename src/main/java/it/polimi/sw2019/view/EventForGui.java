package it.polimi.sw2019.view;

import it.polimi.sw2019.events.NotifyClient;
import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.application.Platform;

public class EventForGui extends Observable<NotifyClient> implements Runnable {

    private ClientSocket clientSocket;

    public EventForGui(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * set the observer, receives a notify return event to set the table
     */
    public void run() {
        boolean goOutTheLoop = true;
        this.addObserver(this.clientSocket.getTableController()) ;

        while(goOutTheLoop) {
            NotifyClient notifyClient = this.clientSocket.getContSelect().waitForNotifyReturnEvent();
            if( notifyClient.getGameBoard() == null) {
                goOutTheLoop = false;
            }
            Platform.runLater( () -> notify( (notifyClient)  ) );
            Platform.runLater( () -> this.clientSocket.notifyGUI("Refresh") );
        }

    }
}
