package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Zx-2, the basic effect of Zx-2
 */
public class ZX2SetEv implements TargetSetEv, ActionEv {
    private String playerNickname;
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of choose event
     */
    public ZX2SetEv(String target) {
        this.target = target;
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

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
