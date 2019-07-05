package it.polimi.sw2019.events.server_event.VCevent;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

public class EndEv implements ActionEv {

    private String nickname;

    public void setPlayerNickname(String nickname){
        this.nickname = nickname;
    }

    public String getPlayerNickname(){
        return this.nickname;
    }

    public void handle(ExecutorEventImp executorEventImp, Game controller){
        executorEventImp.handleObject(this,controller);
    }
}
