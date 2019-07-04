package it.polimi.sw2019.events.powerup_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

public class NewtonSetEv implements ActionEv {
    private String playerNickname;
    private String target;
    private String moveto;

    public NewtonSetEv(String target, String moveto) {
        this.target = target;
        this.moveto = moveto;
    }

    @Override
    public String getPlayerNickname() {
        return playerNickname;
    }

    @Override
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public String getTarget() {
        return target;
    }

    public String getMoveto() {
        return moveto;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
