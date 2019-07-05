package it.polimi.sw2019.events;

import it.polimi.sw2019.controller.Game;

import java.io.Serializable;

public interface ActionEv extends Serializable {


    void setPlayerNickname(String nickname);

    String getPlayerNickname();

    void handle(ExecutorEventImp executorEventImp, Game controller);

}
