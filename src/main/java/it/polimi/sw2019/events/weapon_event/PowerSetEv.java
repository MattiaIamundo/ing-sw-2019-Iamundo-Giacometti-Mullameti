package it.polimi.sw2019.events.weapon_event;

import it.polimi.sw2019.utility.SimplifiedPowerUp;

import java.util.ArrayList;
import java.util.HashMap;

public class PowerSetEv {
    private ArrayList<String> powers;
    private ArrayList<SimplifiedPowerUp> usedPowerUps;

    public PowerSetEv(ArrayList<String> powers, ArrayList<SimplifiedPowerUp> usedPowerUps) {
        this.powers = powers;
        this.usedPowerUps = usedPowerUps;
    }

    public ArrayList<String> getPowers() {
        return powers;
    }

    public ArrayList<SimplifiedPowerUp> getUsedPowerUps() {
        return usedPowerUps;
    }
}
