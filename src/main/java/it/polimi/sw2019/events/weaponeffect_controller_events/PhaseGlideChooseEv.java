package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Phase glide, the first optional effect of Plasma Gun
 */
public class PhaseGlideChooseEv implements WeaponEvent{
    private Player attacker;
    private ArrayList<String> positions;

    /**
     * @param attacker is the player that invoke the effect
     * @param positions is the list of the valid squares in which the attacker can be moved, they are expressed by their cardinal direction e.g. north, north-north, south-east
     */
    public PhaseGlideChooseEv(Player attacker, ArrayList<String> positions) {
        this.attacker = attacker;
        this.positions = positions;
    }

    @Override
    public String getAttacker() {
        return attacker.getNickname();
    }

    public ArrayList<String> getPositions() {
        return positions;
    }
}
