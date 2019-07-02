package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class TractorBeamChooseEv implements WeaponEvent {
    private String attacker;
    private HashMap<String, ArrayList<String>> valid;

    public TractorBeamChooseEv(String attacker, HashMap<String, ArrayList<String>> valid) {
        this.attacker = attacker;
        this.valid = valid;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public HashMap<String, ArrayList<String>> getValid() {
        return valid;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
