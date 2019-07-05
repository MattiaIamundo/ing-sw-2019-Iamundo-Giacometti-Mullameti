package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Tractor beam, the basic effect of Tractor Beam
 */
public class TractorBeamSetEv extends WeaponEffectSetEv {
    private String target;
    private String position;

    /**
     * @param target is the player set as target, must be one of the key of the hash map in the choose event
     * @param position is the cardinal direction in which the target must be moved in, must be one of those contained in the list associated to the target in the hash map
     *                 in the choose event
     */
    public TractorBeamSetEv(String target, String position) {
        this.target = target;
        this.position = position;
    }

    public String getTarget() {
        return target;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
