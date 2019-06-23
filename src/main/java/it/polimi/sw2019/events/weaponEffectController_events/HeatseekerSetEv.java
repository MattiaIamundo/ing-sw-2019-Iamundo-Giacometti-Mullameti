package it.polimi.sw2019.events.weaponEffectController_events;

public class HeatseekerSetEv implements TargetSetEv{

    private String target;

    public HeatseekerSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
