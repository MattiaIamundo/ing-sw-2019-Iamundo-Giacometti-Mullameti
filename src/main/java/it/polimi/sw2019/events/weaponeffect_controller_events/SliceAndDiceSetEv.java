package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Slice and dice, the second optional effect of Cyberblade
 */
public class SliceAndDiceSetEv extends WeaponEffectSetEv{
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of the choose event
     */
    public SliceAndDiceSetEv(String target) {
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
