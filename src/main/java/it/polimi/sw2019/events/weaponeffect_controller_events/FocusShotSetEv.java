package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of the Focus shot, the first optional effect of Machine Gun
 */
public class FocusShotSetEv extends WeaponEffectSetEv{
    private String target;

    /**
     * @param target is the player selected as target, must be one of the two listed in the targets list in the choose event, null if no rooms can be selected
     */
    public FocusShotSetEv(String target) {
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
