package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Vortex cannon, the basic effect of Vortex Cannon
 */
public class VortexSetEv implements ActionEv {
    private String playerNickname;
    private String target;
    private String position;

    /**
     * @param target is the player set as target, must be one of those contained in the list associated to the selected vortex position
     * @param position is the square selected to be "the vortex", it's coordinates must be one of those contained in the hash map of choose event
     */
    public VortexSetEv(String target, String position) {
        this.target = target;
        this.position = position;
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

    public String getPosition() {
        return position;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
