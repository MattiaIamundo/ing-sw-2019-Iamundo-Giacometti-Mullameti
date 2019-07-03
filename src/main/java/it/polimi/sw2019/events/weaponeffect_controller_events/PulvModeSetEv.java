package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Pulverize mode, the alternative effect of Sledgehammer
 */
public class PulvModeSetEv implements ActionEv {
    private String playerNickname;
    private String target;
    private String moveto;

    /**
     * These is is the constructor in case the attacker choose to move the target
     * @param target is the player selected as target, must be one of those contained in the targets list of choose event
     * @param moveto is the cardinal direction in which the attacker intend to move the target, must be one of those contained in the movements list in choose event
     */
    public PulvModeSetEv(String target, String moveto) {
        this.target = target;
        this.moveto = moveto;
    }

    /**
     * These is the constructor in case the attacker choose not to move the target
     * @param target is the player selected as target, must be one of those contained in the targets list of choose event
     */
    public PulvModeSetEv(String target) {
        this.target = target;
        moveto = "zero";
    }

    @Override
    public String getPlayerNickname() {
        return playerNickname;
    }

    @Override
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

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
