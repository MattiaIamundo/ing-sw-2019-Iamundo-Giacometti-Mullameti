package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Cyberblade, the basic effect of Cyberblade
 */
public class CyberbladeSetEv extends TargetSetEv{

    /**
     * @param target is the player selected as the target of the effect, must be one of those contained in the valid list of the choose event
     */
    public CyberbladeSetEv(String target) {
        super(target);
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
