package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Furnace, the basic effect of Furnace
 */
public class FurnaceChooseEv implements WeaponEvent{
    private Player attacker;
    private ArrayList<String> rooms;

    /**
     * @param attacker is the player that invoke the effect
     * @param rooms is the list of the rooms that can be selected as valid target and contains at least one player, it can be empty
     */
    public FurnaceChooseEv(Player attacker, ArrayList<String> rooms) {
        this.attacker = attacker;
        this.rooms = rooms;
    }

    @Override
    public String getAttacker() {
        return attacker.getNickname();
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }
}
