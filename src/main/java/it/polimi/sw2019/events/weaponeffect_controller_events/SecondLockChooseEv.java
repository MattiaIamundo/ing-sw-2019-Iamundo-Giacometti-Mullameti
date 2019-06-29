package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class SecondLockChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> valid;
    private ArrayList<String> notselectable;
    private ArrayList<String> notreachable;

    public SecondLockChooseEv(Player attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable) {
        this.attacker = attacker;
        this.valid = valid;
        this.notselectable = notselectable;
        this.notreachable = notreachable;
    }

    @Override
    public String getAttacker() {
        return attacker.getNickname();
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
}