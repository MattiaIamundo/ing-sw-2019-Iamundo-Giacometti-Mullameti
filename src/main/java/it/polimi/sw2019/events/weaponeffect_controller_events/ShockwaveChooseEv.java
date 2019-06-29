package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class ShockwaveChooseEv implements WeaponEvent{

    private Player attacker;
    private HashMap<String, ArrayList<String>> targets;

    public ShockwaveChooseEv(Player attacker, HashMap<String, ArrayList<String>> targets) {
        this.attacker = attacker;
        this.targets = targets;
    }

    @Override
    public String getAttacker() {
        return attacker.getNickname();
    }

    public HashMap<String, ArrayList<String>> getTargets() {
        return targets;
    }
}
