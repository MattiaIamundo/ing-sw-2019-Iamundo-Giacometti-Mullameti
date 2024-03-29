package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Railgun, the basic effect of Railgun
 */
public class RailGunSetEv extends WeaponEffectSetEv {
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the hash map in the choose event
     */
    public RailGunSetEv(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
