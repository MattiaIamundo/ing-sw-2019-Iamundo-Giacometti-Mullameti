package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * These class represent the choose event of Shockwave, the basic effect of Shockwave
 */
public class ShockwaveChooseEv implements WeaponEvent{
    private Player attacker;
    private HashMap<String, ArrayList<String>> targets;

    /**
     * @param attacker is the player that invoke the effect
     * @param targets the hash map use as key the cardinal direction (e.g. north, south) of the valid squares and as value the list of the player, on the key's square,
     *                that can be selected as a valid target
     */
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
