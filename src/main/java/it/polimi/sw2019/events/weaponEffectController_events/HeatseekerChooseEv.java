package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Heatseeker, the basic effect of Heatseeker
 */
public class HeatseekerChooseEv implements WeaponEvent{
    private Player attacker;
    private ArrayList<String> valid;
    private ArrayList<String> notreachable;

    /**
     * @param attacker is the player that invoke the effect
     * @param valid is the list of the player that can be selected as a valid target for the effect
     * @param notreachable is the list of the player that can't be selected as a target due to be visible to the attacker
     */
    public HeatseekerChooseEv(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable) {
        this.attacker = attacker;
        this.valid = valid;
        this.notreachable = notreachable;
    }

    @Override
    public String getAttacker() {
        return attacker.getNickname();
    }

    public ArrayList<String> getValid() {
        return valid;
    }

    public ArrayList<String> getNotreachable() {
        return notreachable;
    }
}
