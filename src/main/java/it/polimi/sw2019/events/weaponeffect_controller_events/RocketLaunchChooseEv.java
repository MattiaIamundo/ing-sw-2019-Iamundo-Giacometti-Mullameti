package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.Map;

public class RocketLaunchChooseEv implements WeaponEvent{

    private Player attacker;
    private Map<String, ArrayList<String>> targets;

    public RocketLaunchChooseEv(Map<String, ArrayList<String>> targets, Player attacker) {
        this.targets = targets;
        this.attacker = attacker;
    }

    public Map<String, ArrayList<String>> getTargets() {
        return targets;
    }

    public String getAttacker(){
        return attacker.getNickname();
    }
}
