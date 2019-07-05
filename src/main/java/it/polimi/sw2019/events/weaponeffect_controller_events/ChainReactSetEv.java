package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class is the set event of Chain reaction, the first optional effect of T.H.O.R.
 */
public class ChainReactSetEv extends TargetSetEv {

    /**
     * @param target is the selected target, must be one of those contained in the valid list of the choose event
     */
    public ChainReactSetEv(String target) {
        super(target);
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
