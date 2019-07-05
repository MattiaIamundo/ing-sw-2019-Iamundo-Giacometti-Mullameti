package it.polimi.sw2019.events;


import it.polimi.sw2019.controller.Game;

import java.io.Serializable;

public interface NotifyReturn extends Serializable {


    void setNickname(String nickname);

    String getNickname();

    void updateObject(ExecutorEventImp executorEventImp, Game controller);

}
