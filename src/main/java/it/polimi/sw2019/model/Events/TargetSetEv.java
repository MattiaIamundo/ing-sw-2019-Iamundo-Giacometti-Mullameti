package it.polimi.sw2019.model.Events;

public class TargetSetEv implements WeaponEvent{
    protected String target;

    public TargetSetEv(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }
}
