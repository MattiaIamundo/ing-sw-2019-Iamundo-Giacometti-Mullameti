package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * These class represent the choose event of Grenade launcher, the basic effect of Grenade Launcher
 */
public class GrenadeLaunchChooseEv implements WeaponEvent{
    private Player attacker;
    private ArrayList<String> valid;
    private ArrayList<String> notreachable;
    private HashMap<String, ArrayList<String>> moveto;

    /**
     * @param attacker is the player that invoke the effect
     * @param valid is the list of the players that can be selected as a valid target
     * @param notreachable is the list of the players that can't be selected as target due to be out of the effect's range
     * @param moveto this has map use as key the nicknames of the players that can be selected as a target and as value the list of the direction, expressed as cardinal directions e.g. north, south,
     *               in which the player can be moved
     */
    public GrenadeLaunchChooseEv(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable, HashMap<String, ArrayList<String>> moveto) {
        this.attacker = attacker;
        this.valid = valid;
        this.notreachable = notreachable;
        this.moveto = moveto;
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

    public HashMap<String, ArrayList<String>> getMoveto() {
        return moveto;
    }
}
