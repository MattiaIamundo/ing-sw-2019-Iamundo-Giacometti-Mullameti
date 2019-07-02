package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * These class represent the choose event of Grenade launcher, the basic effect of Grenade Launcher
 */
public class GrenadeLaunchChooseEv extends VisibleChooseEv{
    private HashMap<String, ArrayList<String>> moveto;

    /**
     * @param attacker is the player that invoke the effect
     * @param valid is the list of the players that can be selected as a valid target
     * @param notreachable is the list of the players that can't be selected as target due to be out of the effect's range
     * @param moveto this has map use as key the nicknames of the players that can be selected as a target and as value the list of the direction, expressed as cardinal directions e.g. north, south,
     *               in which the player can be moved
     */
    public GrenadeLaunchChooseEv(String attacker, ArrayList<String> valid, ArrayList<String> notreachable, HashMap<String, ArrayList<String>> moveto) {
        super(attacker, valid, notreachable);
        this.moveto = moveto;
    }

    public HashMap<String, ArrayList<String>> getMoveto() {
        return moveto;
    }
}
