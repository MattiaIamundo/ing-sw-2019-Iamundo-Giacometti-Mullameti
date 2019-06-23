package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class PulvModeChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> targets;
    private ArrayList<String> movements;

    public PulvModeChooseEv(ArrayList<String> targets, ArrayList<String> movements, Player attacker) {
        this.targets = targets;
        this.movements = movements;
        this.attacker = attacker;
    }

    public ArrayList<String> getTargets() {
        return targets;
    }

    public ArrayList<String> getMovements() {
        return movements;
    }

    public String getAttacker(){
        return attacker.getNickname();
    }
}
