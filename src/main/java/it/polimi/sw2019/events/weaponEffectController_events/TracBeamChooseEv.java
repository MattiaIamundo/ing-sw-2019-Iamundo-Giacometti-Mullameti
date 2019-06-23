package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class TracBeamChooseEv implements WeaponEvent{

    private Player attacker;
    private HashMap<String, ArrayList<String>> valid;
    private ArrayList<String> notselectable;

    public TracBeamChooseEv(HashMap<String, ArrayList<String>> valid, ArrayList<String> notselectable, Player attacker) {
        this.valid = valid;
        this.notselectable = notselectable;
        this.attacker = attacker;
    }

    public HashMap<String, ArrayList<String>> getValid() {
        return valid;
    }

    public ArrayList<String> getNotselectable() {
        return notselectable;
    }

    public String getAttacker(){
        return attacker.getNickname();
    }
}
