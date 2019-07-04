package it.polimi.sw2019.events.powerup_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;

import java.util.ArrayList;

public class PowerUpChooseEv implements NotifyReturn {
    private String nickname;
    private ArrayList<String> powerUps;

    public PowerUpChooseEv(String nickname, ArrayList<String> powerUps) {
        this.nickname = nickname;
        this.powerUps = powerUps;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<String> getPowerUps() {
        return powerUps;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
