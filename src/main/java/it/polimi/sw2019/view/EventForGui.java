package it.polimi.sw2019.view;

import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.Cevent.StartGameEv;
import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.application.Platform;

public class EventForGui extends Observable<NotifyReturn> implements Runnable {

    private ClientSocket clientSocket;

    public EventForGui(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * set the observer, receives the start event to set the table
     */
    public void run() {

        boolean goOutWhile = false;
        this.addObserver(this.clientSocket.getTableController());
        StartGameEv startGameEv = (StartGameEv) this.clientSocket.getContSelect().waitForStartGameEv();


        Platform.runLater(  () ->    this.clientSocket.getTableController().handleEvent(startGameEv)    );
        Platform.runLater(  () ->    this.clientSocket.notifyGUI("Refresh") );


        /*
        NotifyReturn notifyReturn = this.clientSocket.getContSelect().waitForNotifyReturnEvent();
        Platform.runLater(() -> notify(notifyReturn));
        Platform.runLater(() -> this.clientSocket.notifyGUI("Refresh"));


         */




    }
}
