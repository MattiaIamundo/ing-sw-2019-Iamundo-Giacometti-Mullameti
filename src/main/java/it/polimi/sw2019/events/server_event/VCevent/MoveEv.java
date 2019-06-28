package it.polimi.sw2019.events.server_event.VCevent;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

import java.util.ArrayList;
import java.util.List;

public class MoveEv extends ActionEv {

    private String playerNickname;
    private List<String> moves = new ArrayList<>();

    public MoveEv(List<String> possibleMoves) {
        this.moves = possibleMoves;
    }

    public List<String> getMoves() {
        return this.moves;
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
