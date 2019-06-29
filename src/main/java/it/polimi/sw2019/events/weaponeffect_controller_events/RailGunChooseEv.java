package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * These class represent the choose event of Railgun, the basic effect of Railgun
 */
public class RailGunChooseEv implements WeaponEvent{
    private HashMap<String, ArrayList<String>> targets;
    private Player attacker;

    /**
     * @param targets the hash map use as key the cardinal direction, e.g. north, south, and as value the list of the players that can be selected as a valid
     *                target in the key's direction
     * @param attacker is the player that invoke the effect
     */
    public RailGunChooseEv(HashMap<String, ArrayList<String>> targets, Player attacker) {
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
