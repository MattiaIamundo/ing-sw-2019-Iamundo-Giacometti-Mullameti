package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Flamethrower, the basic effect of Flamethrower
 */
public class FlamethrowerSetEv implements LineFireSetEv, ActionEv {
    private String playerNickname;
    private String target1;
    private String target2;

    /**
     * @param target1 is the player selected as target 1 square away from the attacker, must be in the hash map firstline in the choose event, the direction can be whatever
     * @param target2 is the player selected as target 2 squares away from the attacker, must be in the hash map secondline in the choose event,
     *                must be in the list associated to the same direction of the first target
     */
    public FlamethrowerSetEv(String target1, String target2) {
        this.target1 = target1;
        this.target2 = target2;
    }

    @Override
    public String getPlayerNickname() {
        return playerNickname;
    }

    @Override
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
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
