package it.polimi.sw2019.model.events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class RocketFistChooseEv implements WeaponEvent{

    private Player attacker;
    private HashMap<String, ArrayList<String>> firstlevel;
    private HashMap<String, ArrayList<String>> secondlevel;

    public RocketFistChooseEv(Player attacker, HashMap<String, ArrayList<String>> firstlevel, HashMap<String, ArrayList<String>> secondlevel) {
        this.attacker = attacker;
        this.firstlevel = firstlevel;
        this.secondlevel = secondlevel;
    }

    public HashMap<String, ArrayList<String>> getFirstlevel() {
        return firstlevel;
    }

    public HashMap<String, ArrayList<String>> getSecondlevel() {
        return secondlevel;
    }

    public String getAttacker(){
        return attacker.getNickname();
    }
}
