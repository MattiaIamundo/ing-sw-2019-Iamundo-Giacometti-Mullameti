package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Rocket launcher, the basic effect of Rocket Launcher
 */
public class RocketLaunchSetEv extends WeaponEffectSetEv{
    private String target;
    private String position;

    /**
     * @param target is the player selected as target, must be one of those contained in the key set of the hash map in the choose event
     * @param position is the squares in which the attacker intend to move the target, must be one of those contained in the list associated to the selected target
     *                 in the hash amp in the choose event
     */
    public RocketLaunchSetEv(String target, String position) {
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
