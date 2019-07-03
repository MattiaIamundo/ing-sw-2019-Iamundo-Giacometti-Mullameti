package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Cozy fire mode, the alternative effect of Furnace
 */
public class CozyFireModeSetEv implements ActionEv {
    private String playerNickname;
    private String direction;

    /**
     * @param direction is the cardinal direction of the square selected as the target, must be one of those contained in the list of the choose event
     */
    public CozyFireModeSetEv(String direction) {
        this.direction = direction;
    }

    @Override
    public String getPlayerNickname() {
        return playerNickname;
    }

    @Override
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
