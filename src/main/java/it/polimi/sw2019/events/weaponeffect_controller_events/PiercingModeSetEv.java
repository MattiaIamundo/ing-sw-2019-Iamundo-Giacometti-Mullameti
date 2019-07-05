package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class is the set event of Piercing mode, the alternative effect of Railgun
 */
public class PiercingModeSetEv extends WeaponEffectSetEv {
    private String playerNickname;
    private String target1;
    private String target2;

    /**
     * These is the constructor in case the attacker choose two target
     * @param target1 is the player selected as the first target, must be one of those contained in the hash map targets in the choose event, the direction can be whatever
     * @param target2 is the player selected as the second target, must be one of those contained in the hash map targets in the choose event, the direction must be the same of the
     *                first target and the two target must be different
     */
    public PiercingModeSetEv(String target1, String target2) {
        this.target1 = target1;
        this.target2 = target2;
    }

    /**
     * These ic the constructor in case the attacker choose only one target
     * @param target1 is the player selected as target, must be one of those contained in the hash map targets in the choose event, the direction can be whatever
     */
    public PiercingModeSetEv(String target1) {
        this.target1 = target1;
        this.target2 = null;
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
