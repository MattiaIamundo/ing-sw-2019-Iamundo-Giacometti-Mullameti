package it.polimi.sw2019.model.events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class BarbecueChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> directions;

    public BarbecueChooseEv(Player attacker, ArrayList<String> directions) {
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
