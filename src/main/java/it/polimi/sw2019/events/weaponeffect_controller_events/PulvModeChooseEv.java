package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Pulverize mode, the alternative effect of Sledgehammer
 */
public class PulvModeChooseEv implements WeaponEvent{
    private Player attacker;
    private ArrayList<String> targets;
    private ArrayList<String> movements;

    /**
     * @param targets is the list of the players that can be selected as a valid target
     * @param movements is the list of the square where the target can be moved, they are expressed by their cardinal direction e.g. north, north-north
     * @param attacker is the player that invoke the effect
     */
    public PulvModeChooseEv(ArrayList<String> targets, ArrayList<String> movements, Player attacker) {
        this.targets = targets;
        this.movements = movements;
        this.attacker = attacker;
    }

    public ArrayList<String> getTargets() {
        return targets;
    }

    public ArrayList<String> getMovements() {
        return movements;
    }

    public String getAttacker(){
        return attacker.getNickname();
    }
}
