package it.polimi.sw2019.model.Events;

import it.polimi.sw2019.model.Map;

import java.util.ArrayList;

public class PulvModeChooseEv {
    private ArrayList<String> targets;
    private ArrayList<String> movements;

    public PulvModeChooseEv(ArrayList<String> targets, ArrayList<String> movements) {
        this.targets = targets;
        this.movements = movements;
    }

    public ArrayList<String> getTargets() {
        return targets;
    }

    public ArrayList<String> getMovements() {
        return movements;
    }
}
