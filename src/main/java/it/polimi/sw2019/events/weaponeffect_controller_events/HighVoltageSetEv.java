package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of High voltage, the second optional effect of T.H.O.R.
 */
public class HighVoltageSetEv extends TargetSetEv {
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of the choose event
     */
    public HighVoltageSetEv(String target) {
        super(target);
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
