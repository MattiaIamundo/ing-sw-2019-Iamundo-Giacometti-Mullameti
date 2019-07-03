package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the choose event of Shotgun, the basic effect of Shotgun
 */
public class ShotgunSetEv implements TargetSetEv, ActionEv {
    private String playerNickname;
    private String target;
    private String moveto;

    /**
     * @param target is the player selected as target, must be one those contained in the valid list of the choose event
     * @param moveto is the direction in which the attacker want to move the target, must be one of those contained in the moveto list of choose event,
     *               can be null if no movement is required
     */
    public ShotgunSetEv(String target, String moveto) {
        this.target = target;
        this.moveto = moveto;
    }

    @Override
    public String getPlayerNickname() {
        return playerNickname;
    }

    @Override
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    @Override
    public String getTarget() {
        return target;
    }

    public String getMoveto() {
        return moveto;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
