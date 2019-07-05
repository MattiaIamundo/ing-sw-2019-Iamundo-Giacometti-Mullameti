package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Grenade launcher, the basic effect of Grenade Launcher
 */
public class GrenadeLaunchSetEv extends TargetSetEv {
    private String moveto;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of the choose event
     * @param moveto is the direction in which the attacker intend to move the target, must be one of those contained in the list of the direction associated to the chosen
     *               target in the hash map moveto in the choose event, must be null if the attacker doesn't want to move the target
     */
    public GrenadeLaunchSetEv(String target, String moveto) {
        super(target);
        this.moveto = moveto;
    }

    public GrenadeLaunchSetEv(String target) {
        super(target);
        moveto = null;
    }

    public String getMoveto() {
        return moveto;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
