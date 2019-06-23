package it.polimi.sw2019.events.weaponEffectController_events;

public class CyberbladeSetEv implements TargetSetEv{

    private String target;

    public CyberbladeSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
