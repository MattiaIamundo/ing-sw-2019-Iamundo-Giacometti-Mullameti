package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Sledgehammer, the basic effect of Sledgehammer
 */
public class SledgehammerChooseEv extends VisibleChooseEv{

    /**
     * @param attacker is the player that invoke the effect
     * @param valid is the list of the players that can be selected as a valid target
     * @param notreachable is the list of the players that can't be selected as target due to be out of the effect's range
     */
    public SledgehammerChooseEv(String attacker, ArrayList<String> valid, ArrayList<String> notreachable) {
        super(attacker, valid, notreachable);
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
