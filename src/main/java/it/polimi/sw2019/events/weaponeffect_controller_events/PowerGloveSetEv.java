package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Power glove, the basic effect of Power Glove
 */
public class PowerGloveSetEv extends TargetSetEv {

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of choose event
     */
    public PowerGloveSetEv(String target) {
        super(target);
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
