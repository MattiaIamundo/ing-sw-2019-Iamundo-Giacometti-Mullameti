package it.polimi.sw2019.model.events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class BlackHoleChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> targets;
    private String notselectable;

    public BlackHoleChooseEv(ArrayList<String> targets, String notselectable, Player attacker) {
        this.targets = targets;
        this.notselectable = notselectable;
        this.attacker = attacker;
    }

    public ArrayList<String> getTargets() {
        return targets;
    }

    public String getAttacker(){
        return attacker.getNickname();
    }
}
