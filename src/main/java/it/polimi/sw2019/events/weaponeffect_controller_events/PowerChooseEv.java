package it.polimi.sw2019.events.weaponeffect_controller_events;

import java.util.ArrayList;
import java.util.HashMap;

public class PowerChooseEv {
    private Boolean itsAlternative;
    private HashMap<String,ArrayList<String>> powers;
    private HashMap<String, Integer> availableAmmo;
    private HashMap<String, String> availablePowerUps;

    public PowerChooseEv(Boolean itsAlternative, HashMap<String, ArrayList<String>> powers, HashMap<String, Integer> availableAmmo, HashMap<String, String> availablePowerUps) {
        this.itsAlternative = itsAlternative;
        this.powers = powers;
        this.availableAmmo = availableAmmo;
        this.availablePowerUps = availablePowerUps;
    }

    public Boolean getItsAlternative() {
        return itsAlternative;
    }

    public HashMap<String, ArrayList<String>> getPowers() {
        return powers;
    }

    public HashMap<String, Integer> getAvailableAmmo() {
        return availableAmmo;
    }

    public HashMap<String, String> getAvailablePowerUps() {
        return availablePowerUps;
    }
}
