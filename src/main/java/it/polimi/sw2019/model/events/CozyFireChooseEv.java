package it.polimi.sw2019.model.events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class CozyFireChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> positions;

    public CozyFireChooseEv(ArrayList<String> positions, Player attacker) {
        this.positions = positions;
        this.attacker = attacker;
    }

    public ArrayList<String> getPositions() {
        return positions;
    }

    public String getAttacker(){
        return attacker.getNickname();
    }
}
