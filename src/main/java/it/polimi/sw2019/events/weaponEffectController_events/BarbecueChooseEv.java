package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * This class represent the choose event for Barbecue mode, the alternative effect of Flamethrower.
 */
public class BarbecueChooseEv implements WeaponEvent{
    private Player attacker;
    private ArrayList<String> directions;

    /**
     * @param attacker is the player that invoke the effect
     * @param directions is the list of the directions that can be chose. Listed as cardinal direction e.g. north, west
     */
    public BarbecueChooseEv(Player attacker, ArrayList<String> directions) {
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
