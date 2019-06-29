package it.polimi.sw2019.events.weaponeffect_controller_events;

public class RocketLaunchSetEv {
    private String target;
    private String position;

    public RocketLaunchSetEv(String target, String position) {
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