package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Rocket jump, the first optional effect of Rocket Launcher
 */
public class RocketJumpSetEv extends WeaponEffectSetEv implements MoveTargetSetEv {
    private String moveto;

    /**
     * @param moveto is the square in which the attacker intend to move, must be one of those contained in the positions list oin the choose event
     */
    public RocketJumpSetEv(String moveto) {
        this.moveto = moveto;
    }

    @Override
    public String getMoveto() {
        return moveto;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
