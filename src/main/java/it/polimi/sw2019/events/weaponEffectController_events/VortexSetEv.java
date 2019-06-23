package it.polimi.sw2019.events.weaponEffectController_events;

public class VortexSetEv {
    private String target;
    private String position;

    public VortexSetEv(String target, String position) {
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
