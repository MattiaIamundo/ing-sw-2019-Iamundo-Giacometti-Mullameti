package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * These class represent the choose event of Rocket fist mode, the alternative mode of Power Glove
 */
public class RocketFistChooseEv implements WeaponEvent{
    private Player attacker;
    private HashMap<String, ArrayList<String>> firstlevel;
    private HashMap<String, ArrayList<String>> secondlevel;

    /**
     * @param attacker is the player that invoke the effect
     * @param firstlevel the hash map use as key the squares at 1 move away from the attacker, they are expressed by their cardinal direction e.g. north, south, and as value the list of the
     *                   players that can be selected as a valid target
     * @param secondlevel the hash map use as key the squares at 2 move away in the same direction from the attacker, they are expressed by their cardinal direction e.g. north, south, and as
     *                    value the list of the players that can be selected as a valid target
     */
    public RocketFistChooseEv(Player attacker, HashMap<String, ArrayList<String>> firstlevel, HashMap<String, ArrayList<String>> secondlevel) {
        this.attacker = attacker;
        this.firstlevel = firstlevel;
        this.secondlevel = secondlevel;
    }

    public HashMap<String, ArrayList<String>> getFirstlevel() {
        return firstlevel;
    }

    public HashMap<String, ArrayList<String>> getSecondlevel() {
        return secondlevel;
    }

    public String getAttacker(){
        return attacker.getNickname();
    }
}
