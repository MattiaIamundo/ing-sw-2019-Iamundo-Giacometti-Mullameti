package it.polimi.sw2019.events;


import it.polimi.sw2019.controller.Game;

public interface NotifyReturn {


    void setNickname(String nickname);

    String getNickname();

    void updateObject(ExecutorEventImp executorEventImp, Game controller);


}
