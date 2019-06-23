package it.polimi.sw2019.events.weaponEffectController_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class FocusShotChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> targets;

    public FocusShotChooseEv(Player attacker, ArrayList<String> targets) {
        this.attacker = attacker;
        this.targets = targets;
    }

    @Override
    public String getAttacker() {
        return attacker.getNickname();
    }

    public ArrayList<String> getTargets() {
        return targets;
    }
}
