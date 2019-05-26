package it.polimi.sw2019.model.Events;

import java.util.ArrayList;
import java.util.HashMap;

public class RailGunChooseEv {
    HashMap<String, ArrayList<String>> targets;

    public RailGunChooseEv(HashMap<String, ArrayList<String>> targets) {
        this.targets = targets;
    }

    public HashMap<String, ArrayList<String>> getTargets() {
        return targets;
    }
}
