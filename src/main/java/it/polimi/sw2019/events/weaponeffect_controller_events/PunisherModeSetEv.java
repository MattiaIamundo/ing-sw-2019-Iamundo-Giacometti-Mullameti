package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * these class represent the set event of Punisher mode, the alternative effect of Tractor beam
 */
public class PunisherModeSetEv implements TargetSetEv, ActionEv {
    private String playerNickname;
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of the choose event
     */
    public PunisherModeSetEv(String target) {
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
