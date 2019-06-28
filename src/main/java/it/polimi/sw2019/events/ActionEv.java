package it.polimi.sw2019.events;

import it.polimi.sw2019.controller.Game;

public class ActionEv {

    private String playerNickname = "null";

    public void setPlayerNickname(String nickname) {
        this.playerNickname = nickname;
    }

    public String getPlayerNickname() {
        return this.playerNickname;
    }

    public void handle(ExecutorEventImp executorEventImp, Game controller){}

}
