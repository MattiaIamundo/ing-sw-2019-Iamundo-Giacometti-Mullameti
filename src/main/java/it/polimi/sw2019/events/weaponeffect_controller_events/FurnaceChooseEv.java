package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Furnace, the basic effect of Furnace
 */
public class FurnaceChooseEv implements WeaponEvent{
    private String attacker;
    private ArrayList<String> rooms;

    /**
     * @param attacker is the player that invoke the effect
     * @param rooms is the list of the rooms that can be selected as valid target and contains at least one player, it can be empty
     */
    public FurnaceChooseEv(String attacker, ArrayList<String> rooms) {
        this.attacker = attacker;
        this.rooms = rooms;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
