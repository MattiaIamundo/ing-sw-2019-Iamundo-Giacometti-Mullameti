package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Cozy fire mode, the alternative effect of Furnace
 */
public class CozyFireModeChooseEv implements WeaponEvent{
    private Player attacker;
    private ArrayList<String> directions;

    /**
     * @param attacker is the player that invoke the effect
     * @param directions is the list of the squares that can be selected as target, it's represented by their cardinal direction e.g. north, west
     */
    public CozyFireModeChooseEv(Player attacker, ArrayList<String> directions) {
        this.attacker = attacker;
        this.directions = directions;
    }

    @Override
    public String getAttacker() {
        return attacker.getNickname();
    }

    public ArrayList<String> getDirections() {
        return directions;
    }
}
