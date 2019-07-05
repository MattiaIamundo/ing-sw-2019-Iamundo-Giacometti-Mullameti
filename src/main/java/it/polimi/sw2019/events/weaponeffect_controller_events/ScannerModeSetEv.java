package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Scanner mode, the alternative effect of Zx-2
 */
public class ScannerModeSetEv extends WeaponEffectSetEv {
    private String target1;
    private String target2;
    private String target3;

    /**
     * This is the constructor in case the attacker choose to select 3 targets
     * @param target1 is the player selected as the first target, must be one of those contained in the valid list of the choose event
     * @param target2 is the player selected as the second target, must be one of those contained in the valid list of the choose event
     *                and must be different from the first target
     * @param target3 is the player selected as the third target, must be one of those contained in the valid list of the choose event
     *                and must be different from the first and second target
     */
    public ScannerModeSetEv(String target1, String target2, String target3) {
        this.target1 = target1;
        this.target2 = target2;
        this.target3 = target3;
    }

    /**
     * This is the constructor in case the attacker choose to select 2 targets
     * @param target1 is the player selected as the first target, must be one of those contained in the valid list of the choose event
     * @param target2 is the player selected as the second target, must be one of those contained in the valid list of the choose event
     *                and must be different from the first target
     */
    public ScannerModeSetEv(String target1, String target2) {
        this.target1 = target1;
        this.target2 = target2;
        target3 = null;
    }

    /**
     * These is the constructor in case the attacker choose to select only 1 target
     * @param target1 is the player selected as the first target, must be one of those contained in the valid list of the choose event
     */
    public ScannerModeSetEv(String target1) {
        this.target1 = target1;
        target2 = null;
        target3 = null;
    }

    public String getTarget1() {
        return target1;
    }

    public String getTarget2() {
        return target2;
    }

    public String getTarget3() {
        return target3;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
