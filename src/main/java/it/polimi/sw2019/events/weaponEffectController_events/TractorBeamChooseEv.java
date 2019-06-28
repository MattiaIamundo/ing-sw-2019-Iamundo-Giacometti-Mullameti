package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class TractorBeamChooseEv implements WeaponEvent{

    private Player attacker;
    private HashMap<String, ArrayList<String>> valid;

    public TractorBeamChooseEv(HashMap<String, ArrayList<String>> valid, Player attacker) {
        this.valid = valid;
        this.attacker = attacker;
    }

    public HashMap<String, ArrayList<String>> getValid() {
        return valid;
    }

    public String getAttacker(){
        return attacker.getNickname();
    }
}
