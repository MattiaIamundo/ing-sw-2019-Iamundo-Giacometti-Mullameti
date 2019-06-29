package it.polimi.sw2019.events.weaponeffect_controller_events;

public class TurretTripodSetEv implements TargetSetEv{

    private String target;

    public TurretTripodSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
