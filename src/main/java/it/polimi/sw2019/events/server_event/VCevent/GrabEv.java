package it.polimi.sw2019.events.server_event.VCevent;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

public class GrabEv extends ActionEv {

    private String move;

    public GrabEv(String move) {
        this.move = move;
    }

    public String getMove() {
        return this.move;
    }

    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
