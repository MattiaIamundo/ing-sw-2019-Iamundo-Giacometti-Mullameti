package it.polimi.sw2019.events.weaponEffectController_events;

public class FocusShotSetEv {

    private String target;

    public FocusShotSetEv(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }
}
