package it.polimi.sw2019.events.weaponeffect_controller_events;

public class ZX2SetEv implements TargetSetEv{

    private String target;

    public ZX2SetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}