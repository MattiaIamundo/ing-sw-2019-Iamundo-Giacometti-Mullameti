package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Furnace, the basic effect of Furnace
 */
public class FurnaceSetEv implements ActionEv {
    private String playerNickname;
    private String room;

    /**
     * @param room is the room selected as target, must be one of those contained in the rooms list of the choose event, can be null if there isn't available rooms
     */
    public FurnaceSetEv(String room) {
        this.room = room;
    }

    @Override
    public String getPlayerNickname() {
        return playerNickname;
    }

    @Override
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
