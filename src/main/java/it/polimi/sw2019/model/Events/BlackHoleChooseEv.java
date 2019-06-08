package it.polimi.sw2019.model.Events;

import java.util.ArrayList;

public class BlackHoleChooseEv implements WeaponEvent{
    private ArrayList<String> targets;
    private String notselectable;

    public BlackHoleChooseEv(ArrayList<String> targets, String notselectable) {
        this.targets = targets;
        this.notselectable = notselectable;
    }

    public ArrayList<String> getTargets() {
        return targets;
    }
}
