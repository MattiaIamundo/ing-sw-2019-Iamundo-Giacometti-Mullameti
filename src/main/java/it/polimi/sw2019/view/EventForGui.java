package it.polimi.sw2019.view;

import it.polimi.sw2019.events.NotifyClient;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.Cevent.StartGameEv;
import it.polimi.sw2019.network.Socket.ClientSocket;
import javafx.application.Platform;

public class EventForGui extends Observable<NotifyClient> implements Runnable {

    private ClientSocket clientSocket;

    public EventForGui(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {



            boolean goOutWhile = false;
            this.addObserver(this.clientSocket.getTableController());
            StartGameEv startGameEv = (StartGameEv) this.clientSocket.getContSelect().waitForNotifyReturnEvent();


        Platform.runLater( () ->  this.clientSocket.getTableController().handleEvent(startGameEv)    );
        Platform.runLater( () -> this.clientSocket.notifyGUI("Refresh") );

            /*while(!goOutWhile) {
                NotifyClient notifyReturn =  this.clientSocket.getContSelect().waitForNotifyReturnEvent();
                notify(notifyReturn);

            }

             */





    }
}
