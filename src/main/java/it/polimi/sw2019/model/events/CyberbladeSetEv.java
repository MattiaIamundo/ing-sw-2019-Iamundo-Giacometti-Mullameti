package it.polimi.sw2019.model.events;

public class CyberbladeSetEv implements TargetSetEv{

    private String target;

    public CyberbladeSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
