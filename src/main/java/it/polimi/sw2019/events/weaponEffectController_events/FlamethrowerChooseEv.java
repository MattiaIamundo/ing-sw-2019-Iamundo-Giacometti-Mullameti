package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class FlamethrowerChooseEv implements WeaponEvent{

    private Player attacker;
    private HashMap<String, ArrayList<String>> firstline;
    private HashMap<String, ArrayList<String>> secondline;

    public FlamethrowerChooseEv(Player attacker, HashMap<String, ArrayList<String>> firstline, HashMap<String, ArrayList<String>> secondline) {
        this.attacker = attacker;
        this.firstline = firstline;
        this.secondline = secondline;
    }

    public HashMap<String, ArrayList<String>> getFirstline() {
        return firstline;
    }

    public HashMap<String, ArrayList<String>> getSecondline() {
        return secondline;
    }

    public String getAttacker(){
        return attacker.getNickname();
    }
}
