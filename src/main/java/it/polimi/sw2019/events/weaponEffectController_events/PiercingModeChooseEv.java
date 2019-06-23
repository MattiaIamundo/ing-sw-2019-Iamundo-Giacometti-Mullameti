package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class PiercingModeChooseEv implements WeaponEvent{
    private Player attacker;
    private HashMap<String, ArrayList<String>> targets;

    public PiercingModeChooseEv(HashMap<String, ArrayList<String>> targets, Player attacker) {
        this.targets = targets;
        this.attacker = attacker;
    }

    public HashMap<String, ArrayList<String>> getTargets() {
        return targets;
    }

    public String getAttacker(){
        return attacker.getNickname();
    }
}
