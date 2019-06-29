package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.Map;

/**
 * These class represent the choose event of Rocket launcher, the basic effect of Rocket Launcher
 */
public class RocketLaunchChooseEv implements WeaponEvent{
    private Player attacker;
    private Map<String, ArrayList<String>> targets;

    /**
     * @param targets the hash map use as key the nickname of the players that can be selected as a valid target, and as value the list of the squares in which the key's player can be moved
     *                in, the squares are expressed by their cardinal direction e.g. north, south
     * @param attacker is the player that invoke the effect
     */
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
