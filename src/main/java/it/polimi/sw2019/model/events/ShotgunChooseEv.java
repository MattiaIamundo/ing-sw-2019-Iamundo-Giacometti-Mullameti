package it.polimi.sw2019.model.events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class ShotgunChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> valid;
    private ArrayList<String> notreachable;
    private ArrayList<String> moveto;

    public ShotgunChooseEv(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable, ArrayList<String> moveto) {
        this.attacker = attacker;
        this.valid = valid;
        this.notreachable = notreachable;
        this.moveto = moveto;
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

    public ArrayList<String> getMoveto() {
        return moveto;
    }
}
