package it.polimi.sw2019.events.weaponEffectController_events;

public class ChainReactSetEv implements TargetSetEv{

    private String target;

    public ChainReactSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
