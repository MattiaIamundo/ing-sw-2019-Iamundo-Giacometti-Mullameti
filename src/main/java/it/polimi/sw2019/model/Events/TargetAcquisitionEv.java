package it.polimi.sw2019.model.Events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class TargetAcquisitionEv {
    private Player attacker;
    private ArrayList<String> valid;
    private ArrayList<String> notselectable;
    private ArrayList<String> notreachable;
    private String message;

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
