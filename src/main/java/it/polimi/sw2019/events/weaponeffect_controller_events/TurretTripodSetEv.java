package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class is the set event of Turret tripod, the second optional effect of Machine Gun
 */
public class TurretTripodSetEv extends TargetSetEv {

    /**
     * @param target is the player selected as the extra target, must be one of those contained in the valid list of choose event, can be null
     */
    public TurretTripodSetEv(String target) {
        super(target);
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
