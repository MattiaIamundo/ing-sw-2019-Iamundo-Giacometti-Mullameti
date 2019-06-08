package it.polimi.sw2019.model.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RocketLaunchChooseEv {
    private Map<String, ArrayList<String>> targets = new HashMap<>();

    public RocketLaunchChooseEv(Map<String, ArrayList<String>> targets) {
        this.targets = targets;
    }

    public Map<String, ArrayList<String>> getTargets() {
        return targets;
    }
}
