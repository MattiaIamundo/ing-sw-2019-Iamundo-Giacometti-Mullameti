package it.polimi.sw2019.model.events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class TargetAcquisitionEv implements WeaponEvent{
    protected Player attacker;
    protected ArrayList<String> valid;
    protected ArrayList<String> notselectable;
    protected ArrayList<String> notreachable;
    protected String message;

    public TargetAcquisitionEv(Player attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable, String message) {
        this.attacker = attacker;
        this.valid = valid;
        this.notselectable = notselectable;
        this.notreachable = notreachable;
        this.message = message;
    }

    public Player getAttacker() {
        return attacker;
    }

    public ArrayList<String> getValid() {
        return valid;
    }

    public ArrayList<String> getNotselectable() {
        return notselectable;
    }

    public ArrayList<String> getNotreachable() {
        return notreachable;
    }

    public String getMessage() {
        return message;
    }
}
