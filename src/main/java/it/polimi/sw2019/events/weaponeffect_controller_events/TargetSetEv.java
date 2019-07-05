package it.polimi.sw2019.events.weaponeffect_controller_events;

public abstract class TargetSetEv extends WeaponEffectSetEv{
    protected String target;

    public TargetSetEv(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }
}
