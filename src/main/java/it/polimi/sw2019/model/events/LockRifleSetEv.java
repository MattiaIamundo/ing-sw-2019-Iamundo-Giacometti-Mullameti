package it.polimi.sw2019.model.events;

public class LockRifleSetEv implements TargetSetEv{

    private String target;

    public LockRifleSetEv(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }
}
