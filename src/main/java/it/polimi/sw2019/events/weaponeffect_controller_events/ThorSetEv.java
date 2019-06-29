package it.polimi.sw2019.events.weaponeffect_controller_events;

public class ThorSetEv implements TargetSetEv{

    private String target;

    public ThorSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
