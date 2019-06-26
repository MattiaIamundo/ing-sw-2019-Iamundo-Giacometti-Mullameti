package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GrenadeLaunchChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> valid;
    private ArrayList<String> notreachable;
    private HashMap<String, ArrayList<String>> moveto;

    public GrenadeLaunchChooseEv(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable, HashMap<String, ArrayList<String>> moveto) {
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

    public HashMap<String, ArrayList<String>> getMoveto() {
        return moveto;
    }
}
