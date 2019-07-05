package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Hellion, the basic effect of Hellion
 */
public class HellionSetEv extends TargetSetEv {

    /**
     * @param target is the player selected as target, it must be on of those contained in the valid list of the choose event
     */
    public HellionSetEv(String target) {
        super(target);
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
