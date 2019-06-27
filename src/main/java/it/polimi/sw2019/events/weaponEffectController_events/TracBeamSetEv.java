package it.polimi.sw2019.events.weaponEffectController_events;

import java.io.Serializable;

public class TracBeamSetEv implements Serializable {
    private String target;
    private String position;

    public TracBeamSetEv(String target, String position) {
        this.target = target;
        this.position = position;
    }

    public String getTarget() {
        return target;
    }

    public String getPosition() {
        return position;
    }
}