package it.polimi.sw2019.events.weaponeffect_controller_events;

import java.util.ArrayList;

public class PowerSetEv {
    private ArrayList<String> powers;
    private ArrayList<String> usedPowerUps;

    public PowerSetEv(ArrayList<String> powers, ArrayList<String> usedPowerUps) {
        this.powers = powers;
        this.usedPowerUps = usedPowerUps;
    }

    public ArrayList<String> getPowers() {
        return powers;
    }

    public ArrayList<String> getUsedPowerUps() {
        return usedPowerUps;
    }
}
