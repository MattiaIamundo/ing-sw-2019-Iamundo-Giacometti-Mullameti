package it.polimi.sw2019.events.weaponEffectController_events;

public class HellionSetEv implements TargetSetEv{

    private String target;

    public HellionSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
