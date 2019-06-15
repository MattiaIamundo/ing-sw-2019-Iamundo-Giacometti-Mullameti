package it.polimi.sw2019.model.events;

public class PowerGloveSetEv implements TargetSetEv{

    private String target;

    public PowerGloveSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
