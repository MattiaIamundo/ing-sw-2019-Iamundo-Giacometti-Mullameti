package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * These class represent the choose event of Piercing mode, the alternative effect of Railgun
 */
public class PiercingModeChooseEv implements WeaponEvent{
    private Player attacker;
    private HashMap<String, ArrayList<String>> targets;

    /**
     * @param targets the hash map use as key the cardinal direction and as corresponding value the list of the players that can be selected as a valid target choosing this direction.
     *                The directions are expressed as cardinal directions e.g. north, east
     * @param attacker is the player that invoke the effect
     */
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
