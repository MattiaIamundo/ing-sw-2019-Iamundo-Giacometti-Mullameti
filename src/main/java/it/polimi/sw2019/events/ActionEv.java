package it.polimi.sw2019.events;

import it.polimi.sw2019.controller.Game;

public interface ActionEv {


    void setPlayerNickname(String nickname);

    String getPlayerNickname();

    void handle(ExecutorEventImp executorEventImp, Game controller);

}
