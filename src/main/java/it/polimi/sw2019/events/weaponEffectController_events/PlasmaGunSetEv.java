package it.polimi.sw2019.events.weaponEffectController_events;

public class PlasmaGunSetEv implements TargetSetEv{

    private String target;

    public PlasmaGunSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
