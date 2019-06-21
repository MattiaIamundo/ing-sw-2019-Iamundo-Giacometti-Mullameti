package it.polimi.sw2019.model.events;

public class LongBarrelSetEv implements TargetSetEv{

    private String target;

    public LongBarrelSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}