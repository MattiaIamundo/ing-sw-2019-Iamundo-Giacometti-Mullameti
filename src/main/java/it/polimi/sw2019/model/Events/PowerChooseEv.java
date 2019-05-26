package it.polimi.sw2019.model.Events;

import java.util.ArrayList;

public class PowerChooseEv {
    ArrayList<String> power = new ArrayList<>();

    public PowerChooseEv(ArrayList<String> power) {
        this.power = power;
    }

    public ArrayList<String> getPower() {
        return power;
    }
}
