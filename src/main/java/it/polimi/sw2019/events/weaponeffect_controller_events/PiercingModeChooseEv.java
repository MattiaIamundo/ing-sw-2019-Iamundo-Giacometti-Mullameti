package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * These class represent the choose event of Piercing mode, the alternative effect of Railgun
 */
public class PiercingModeChooseEv extends ThoughWallChooseEv{

    /**
     *
     * @param attacker is the player that invoke the effect
     * @param targets the hash map use as key the cardinal direction and as corresponding value the list of the players that can be selected as a valid target choosing this direction.
     *                The directions are expressed as cardinal directions e.g. north, east
     */
    public PiercingModeChooseEv(String attacker, HashMap<String, ArrayList<String>> targets) {
        super(attacker, targets);
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
