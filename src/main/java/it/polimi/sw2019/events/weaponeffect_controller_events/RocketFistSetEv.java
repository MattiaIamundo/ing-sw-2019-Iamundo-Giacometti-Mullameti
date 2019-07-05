package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Rocket fist mode, the alternative effect of Power Glove
 */
public class RocketFistSetEv extends WeaponEffectSetEv implements LineFireSetEv {
    private String direction;
    private int moveAmount;
    private String target1;
    private String target2;

    /**
     * @param direction is the direction in which the attacker intend to move, it must be one of those contained in the key set of the hash map in the choose event
     * @param moveAmount indicates how many squares the attacker intends to move, can be 1 or 2
     * @param target1 is the player selected as first target, must be in the firstlevel hash map in the choose event, the direction must be the same indicated in the directions parameter
     * @param target2 is the player selected as second target, must be in the secondlevel hash map in the choose event, the direction must be the same of direction parameter
     */
    public RocketFistSetEv(String direction, int moveAmount, String target1, String target2) {
        this.direction = direction;
        this.moveAmount = moveAmount;
        this.target1 = target1;
        this.target2 = target2;
    }

    public String getDirection() {
        return direction;
    }

    public int getMoveAmount() {
        return moveAmount;
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
