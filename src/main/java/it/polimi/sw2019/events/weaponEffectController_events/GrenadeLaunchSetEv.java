package it.polimi.sw2019.events.weaponEffectController_events;

public class GrenadeLaunchSetEv implements TargetSetEv{

    private String target;
    private String moveto;

    public GrenadeLaunchSetEv(String target, String moveto) {
        this.target = target;
        this.moveto = moveto;
    }

    public String getTarget() {
        return target;
    }

    public String getMoveto() {
        return moveto;
    }
}
