package it.polimi.sw2019.events.weaponeffect_controller_events;

public class SledgehammerSetEv implements TargetSetEv{

    private String target;

    public SledgehammerSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
