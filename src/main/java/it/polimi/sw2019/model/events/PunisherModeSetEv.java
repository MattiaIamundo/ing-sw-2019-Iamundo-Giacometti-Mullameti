package it.polimi.sw2019.model.events;

public class PunisherModeSetEv implements TargetSetEv{

    private String target;

    public PunisherModeSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
