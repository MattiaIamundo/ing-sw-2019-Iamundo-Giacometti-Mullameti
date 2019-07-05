package it.polimi.sw2019.events.powerup_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;

import java.util.ArrayList;

public class TargetingScopeChooseEv implements PowerUpEffectChooseEv {
    private String user;
    private ArrayList<String> targets;
    private ArrayList<String> ammos;

    public TargetingScopeChooseEv(String user, ArrayList<String> targets, ArrayList<String> ammos) {
        this.user = user;
        this.targets = targets;
        this.ammos = ammos;
    }

    @Override
    public void setNickname(String nickname) {
        user = nickname;
    }

    @Override
    public String getNickname() {
        return user;
    }

    public ArrayList<String> getTargets() {
        return targets;
    }

    public ArrayList<String> getAmmos() {
        return ammos;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
