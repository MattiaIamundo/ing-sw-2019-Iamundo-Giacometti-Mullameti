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

    public void run() {


        Platform.runLater( () -> {
            boolean goOutWhile = false;

            //this.addObserver(this.clientSocket.getTableController());

            StartGameEv startGameEv = (StartGameEv) this.clientSocket.getContSelect().waitForNotifyReturnEvent();
            this.clientSocket.getTableController().handleEvent(startGameEv);

            this.clientSocket.notifyGUI("Refresh");

            /*while(!goOutWhile) {
                NotifyReturn notifyReturn =  this.clientSocket.getContSelect().waitForNotifyReturnEvent();
                notify(notifyReturn);

            }

             */
        }   );




    }
}
