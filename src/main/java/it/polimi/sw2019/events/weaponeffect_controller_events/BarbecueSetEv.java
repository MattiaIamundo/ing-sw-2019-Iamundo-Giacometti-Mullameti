package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * This class represent the select event for Barbecue mode, the alternative effect of Flamethrower
 */
public class BarbecueSetEv implements ActionEv {
    private String playerNickname;
    private String direction;

    /**
     * @param direction the cardinal direction selected by the attacker, must be one of those contained in the list of the choose event
     */
    public BarbecueSetEv(String direction) {
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
