package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class VortexChooseEv implements WeaponEvent{
    private String attacker;
    private HashMap<String, ArrayList<String>> validchoices;

    public VortexChooseEv(String attacker, HashMap<String, ArrayList<String>> validchoices) {
        this.attacker = attacker;
        this.validchoices = validchoices;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public HashMap<String, ArrayList<String>> getValidchoices() {
        return validchoices;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
