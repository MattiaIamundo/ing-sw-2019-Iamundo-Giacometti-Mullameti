package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Black hole, the optional effect of Vortex Cannon
 */
public class BlackHoleChooseEv extends VisibleChooseEv {

    /**
     * @param attacker the player the invoke the effect
     * @param valid is the list of the players that can be selected as a valid target for the effect
     * @param notselectable is the list of the players that can't be selected as a valid target, in first position there is the attacker, the other is the
     *                      player selected as target for the basic effect of the weapon
     * @param notreachable is the list of the players that can't be selected as a valid target due to be out of the range of the effect
     */
    public BlackHoleChooseEv(String attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable) {
        super(attacker, valid, notselectable, notreachable);
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
