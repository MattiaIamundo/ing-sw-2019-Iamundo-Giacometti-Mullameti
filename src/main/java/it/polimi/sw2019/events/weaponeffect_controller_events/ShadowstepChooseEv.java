package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class ShadowstepChooseEv implements WeaponEvent{

    private Player attacker;
    private ArrayList<String> positions;

    public ShadowstepChooseEv(Player attacker, ArrayList<String> positions) {
        this.attacker = attacker;
        this.positions = positions;
    }

    @Override
    public String getAttacker() {
        return attacker.getNickname();
    }

    public ArrayList<String> getPositions() {
        return positions;
    }
}
