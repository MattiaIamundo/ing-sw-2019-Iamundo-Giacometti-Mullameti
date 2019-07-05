package it.polimi.sw2019.view;

import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.network.Socket.ClientSocket;

public class EventForServer implements Runnable, Observer<ActionEv> {

    private ClientSocket clientSocket;

    public EventForServer(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void update(ActionEv actionEv) {
        this.clientSocket.getViewContEvent().sendActionEv(actionEv);
    }

    public void run() {

    }
}
