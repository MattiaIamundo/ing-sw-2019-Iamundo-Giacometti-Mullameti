package it.polimi.sw2019.events.weaponEffectController_events;

public class NanoTracerSetEv implements TargetSetEv{

    private String target;

    public NanoTracerSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
