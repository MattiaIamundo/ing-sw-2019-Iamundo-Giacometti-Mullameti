package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Machine gun, the basic effect of Machine Gun
 */
public class MachineGunSetEv extends WeaponEffectSetEv {
    private String target1;
    private String target2;

    /**
     * These constructor is used in case the attacker choose two player as targets
     * @param target1 is the player selected as the first target, must be one of those contained in the valid list of choose event
     * @param target2 is the player selected as the second target, must be one of those contained in the valid list of choose event and must be different from the first target
     */
    public MachineGunSetEv(String target1, String target2) {
        this.target1 = target1;
        this.target2 = target2;
    }

    /**
     * These constructor is used in case the attacker choose only one target
     * @param target1 is the player selected as target, must be one of those contained in the valid list of choose event
     */
    public MachineGunSetEv(String target1) {
        this.target1 = target1;
        target2 = null;
    }

    public String getTarget1() {
        return target1;
    }

    public String getTarget2() {
        return target2;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
