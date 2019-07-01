package it.polimi.sw2019.events.server_event.VCevent;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;


public class MoveEv implements ActionEv {

    private String playerNickname = "null";
    private String move;

    public MoveEv(String move) {
        this.move = move;
    }

    @Override
    public String getPlayerNickname() {
        return playerNickname;
    }

    @Override
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public String getMove() {
        return this.move;
    }

    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
