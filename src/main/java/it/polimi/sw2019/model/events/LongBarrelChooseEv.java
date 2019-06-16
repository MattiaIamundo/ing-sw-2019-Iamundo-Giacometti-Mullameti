package it.polimi.sw2019.model.events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class LongBarrelChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> valid;
    private ArrayList<String>notreachable;

    public LongBarrelChooseEv(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable) {
        this.attacker = attacker;
        this.valid = valid;
        this.notreachable = notreachable;
    }

    @Override
    public String getAttacker() {
        return attacker.getNickname();
    }

    public ArrayList<String> getValid() {
        return valid;
    }

    public ArrayList<String> getNotreachable() {
        return notreachable;
    }
}
