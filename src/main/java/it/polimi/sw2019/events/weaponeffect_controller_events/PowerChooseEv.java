package it.polimi.sw2019.events.weaponeffect_controller_events;

import java.util.ArrayList;

public class PowerChooseEv {
    ArrayList<String> power;

    public PowerChooseEv(ArrayList<String> power) {
        this.power = power;
    }

    public ArrayList<String> getPower() {
        return power;
    }
}