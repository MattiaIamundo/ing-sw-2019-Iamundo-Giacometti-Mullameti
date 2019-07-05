package it.polimi.sw2019.events.powerup_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

public class TargetingScopeSetEv implements ActionEv {
    private String playerNickname;
    private String target;
    private String ammo;

    public TargetingScopeSetEv(String target, String ammo) {
        this.target = target;
        this.ammo = ammo;
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

    public String getAmmo() {
        return ammo;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
