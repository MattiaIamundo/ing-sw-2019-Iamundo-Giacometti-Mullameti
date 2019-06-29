package it.polimi.sw2019.events.server_event.VCevent;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;


public class MoveEv extends ActionEv {

    private String playerNickname;
    private String move;

    public MoveEv(String move) {
        this.move = move;
    }

    public String getMove() {
        return this.move;
    }

    public void setPlayerNickname(String nickname) {
        this.playerNickname = nickname;
    }

    public String getPlayerNickname() {
        return this.playerNickname;
    }

    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
