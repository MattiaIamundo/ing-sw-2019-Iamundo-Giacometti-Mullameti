package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class CozyFireModeChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> directions;

    public CozyFireModeChooseEv(Player attacker, ArrayList<String> directions) {
        this.attacker = attacker;
        this.directions = directions;
    }

    @Override
    public String getAttacker() {
        return attacker.getNickname();
    }

    public ArrayList<String> getDirections() {
        return directions;
    }
}
