package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the choose event of Sledgehammer, the basic effect of Sledgehammer
 */
public class SledgehammerSetEv extends TargetSetEv {

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list in the choose event
     */
    public SledgehammerSetEv(String target) {
        super(target);
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
