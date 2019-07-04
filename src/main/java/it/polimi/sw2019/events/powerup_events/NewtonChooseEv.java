package it.polimi.sw2019.events.powerup_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;

import java.util.ArrayList;
import java.util.HashMap;

public class NewtonChooseEv implements PowerUpEffectChooseEv {
    private String user;
    private HashMap<String, ArrayList<String>> movements;

    public NewtonChooseEv(String user, HashMap<String, ArrayList<String>> movements) {
        this.user = user;
        this.movements = movements;
    }

    @Override
    public void setNickname(String nickname) {
        user = nickname;
    }

    @Override
    public String getNickname() {
        return user;
    }

    public HashMap<String, ArrayList<String>> getMovements() {
        return movements;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
