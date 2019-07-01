package it.polimi.sw2019.events.weaponeffect_controller_events;

import java.util.ArrayList;
import java.util.HashMap;

public class PowerSetEv {
    private ArrayList<String> powers;
    private HashMap<String, String> usedPowerUps;

    public PowerSetEv(ArrayList<String> powers, HashMap<String, String> usedPowerUps) {
        this.powers = powers;
        this.usedPowerUps = usedPowerUps;
    }

    public ArrayList<String> getPowers() {
        return powers;
    }

    public HashMap<String, String> getUsedPowerUps() {
        return usedPowerUps;
    }
}
