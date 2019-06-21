package it.polimi.sw2019.model.events;

public class ChainReactSetEv implements TargetSetEv{

    private String target;

    public ChainReactSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
