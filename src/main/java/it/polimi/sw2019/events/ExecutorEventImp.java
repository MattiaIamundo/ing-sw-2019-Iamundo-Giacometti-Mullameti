package it.polimi.sw2019.events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.server_event.VCevent.MoveEv;
import it.polimi.sw2019.events.server_event.VCevent.ReloadEv;

public class ExecutorEventImp implements ExecutorEvent {

    public ExecutorEventImp() {

    }

    public void handleObject(MoveEv moveEv, Game game){
        game.handleEvent(moveEv);
    }

    public void handleObject(ReloadEv reloadEv, Game game){

    }
}
