package it.polimi.sw2019.events;

import it.polimi.sw2019.controller.Game;

import java.util.ArrayList;

public class PowerUpChooseEv implements NotifyReturn{
    private String player;
    private ArrayList<String> powerUps;

    public PowerUpChooseEv(String player, ArrayList<String> powerUps) {
        this.player = player;
        this.powerUps = powerUps;
    }

    @Override
    public void setNickname(String nickname) {
        player = nickname;
    }

    @Override
    public String getNickname() {
        return player;
    }

    public ArrayList<String> getPowerUps() {
        return powerUps;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
