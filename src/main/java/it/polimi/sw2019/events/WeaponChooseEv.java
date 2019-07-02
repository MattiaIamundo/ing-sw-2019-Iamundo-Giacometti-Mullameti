package it.polimi.sw2019.events;

import java.util.ArrayList;

public class WeaponChooseEv {
    private String attacker;
    private ArrayList<String> weapons;

    public WeaponChooseEv(String attacker, ArrayList<String> weapons) {
        this.attacker = attacker;
        this.weapons = weapons;
    }

    public String getAttacker() {
        return attacker;
    }

    public ArrayList<String> getWeapons() {
        return weapons;
    }
}
