package it.polimi.sw2019.model.events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class VortexChooseEv implements WeaponEvent{

    private Player attacker;
    private HashMap<String, ArrayList<String>> validchoices;

    public VortexChooseEv(HashMap<String, ArrayList<String>> validchoices, Player attacker) {
        this.validchoices = validchoices;
        this.attacker = attacker;
    }

    public HashMap<String, ArrayList<String>> getValidchoices() {
        return validchoices;
    }

    public String getAttacker(){
        return attacker.getNickname();
    }
}
