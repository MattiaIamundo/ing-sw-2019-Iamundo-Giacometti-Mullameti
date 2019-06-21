package it.polimi.sw2019.model.events;

public class HighVoltageSetEv implements TargetSetEv{

    private String target;

    public HighVoltageSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
