package it.polimi.sw2019.model.events;

public class SecondLockSetEv implements TargetSetEv{

    private String target;

    public SecondLockSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
