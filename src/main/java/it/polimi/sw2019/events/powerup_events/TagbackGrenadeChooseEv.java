package it.polimi.sw2019.events.powerup_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;

public class TagbackGrenadeChooseEv implements PowerUpEffectChooseEv {
    private String user;
    private String target;

    public TagbackGrenadeChooseEv(String user, String target) {
        this.user = user;
        this.target = target;
    }

    @Override
    public void setNickname(String nickname) {
        user = nickname;
    }

    @Override
    public String getNickname() {
        return user;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
