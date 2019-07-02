package it.polimi.sw2019.events.weaponeffect_controller_events;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ThoughWallChooseEv implements WeaponEvent{
    protected String attacker;
    protected HashMap<String, ArrayList<String>> targets;

    public ThoughWallChooseEv(String attacker, HashMap<String, ArrayList<String>> targets) {
        this.attacker = attacker;
        this.targets = targets;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public HashMap<String, ArrayList<String>> getTargets() {
        return targets;
    }

}
