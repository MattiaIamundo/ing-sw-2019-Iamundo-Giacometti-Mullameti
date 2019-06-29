package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Black hole, the optional effect of Vortex Cannon
 */
public class BlackHoleChooseEv implements WeaponEvent {
    private Player attacker;
    private ArrayList<String> valid;
    private ArrayList<String> notselectable;
    private ArrayList<String> notreachable;

    /**
     * @param attacker the player the invoke the effect
     * @param valid is the list of the players that can be selected as a valid target for the effect
     * @param notselectable is the list of the players that can't be selected as a valid target, in first position there is the attacker, the other is the
     *                      player selected as target for the basic effect of the weapon
     * @param notreachable is the list of the players that can't be selected as a valid target due to be out of the range of the effect
     */
    public BlackHoleChooseEv(Player attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable) {
        this.attacker = attacker;
        this.valid = valid;
        this.notselectable = notselectable;
        this.notreachable = notreachable;
    }

    public ArrayList<String> getValid() {
        return valid;
    }

    public String getAttacker() {
        return attacker.getNickname();
    }

    public ArrayList<String> getNotselectable() {
        return notselectable;
    }

    public ArrayList<String> getNotreachable() {
        return notreachable;
    }
}
