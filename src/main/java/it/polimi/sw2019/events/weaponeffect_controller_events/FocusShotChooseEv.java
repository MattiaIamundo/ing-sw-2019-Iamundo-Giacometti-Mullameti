package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Focus shot, the first optional effect of Machine Gun
 */
public class FocusShotChooseEv implements WeaponEvent{
    private Player attacker;
    private ArrayList<String> targets;

    /**
     * @param attacker is the player that invoke the effect
     * @param targets is the list of the player that can be selected as valid target, they are the two player selected as target for the basic effect
     */
    public FocusShotChooseEv(Player attacker, ArrayList<String> targets) {
        this.attacker = attacker;
        this.targets = targets;
    }

    @Override
    public String getAttacker() {
        return attacker.getNickname();
    }

    public ArrayList<String> getTargets() {
        return targets;
    }
}
