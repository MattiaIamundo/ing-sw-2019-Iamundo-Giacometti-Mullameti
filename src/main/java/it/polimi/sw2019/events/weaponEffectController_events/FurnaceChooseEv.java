package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class FurnaceChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> rooms;

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
