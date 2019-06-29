package it.polimi.sw2019.events.powerup_events;

import java.util.ArrayList;

public class TargetingScopeChooseEv implements PowerUpChooseEv{
    private String user;
    private ArrayList<String> targets;

    public TargetingScopeChooseEv(String user, ArrayList<String> targets) {
        this.user = user;
        this.targets = targets;
    }

    @Override
    public String getUser() {
        return user;
    }

    public ArrayList<String> getTargets() {
        return targets;
    }
}
