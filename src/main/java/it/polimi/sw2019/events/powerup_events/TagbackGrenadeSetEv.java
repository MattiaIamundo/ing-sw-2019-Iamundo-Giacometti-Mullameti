package it.polimi.sw2019.events.powerup_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

public class TagbackGrenadeSetEv implements ActionEv {
    private String playerNickname;
    private Boolean markTarget;

    public TagbackGrenadeSetEv(Boolean markTarget) {
        this.markTarget = markTarget;
    }

    @Override
    public String getPlayerNickname() {
        return playerNickname;
    }

    @Override
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public Boolean getMarkTarget() {
        return markTarget;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
