package it.polimi.sw2019.model.events;

import it.polimi.sw2019.model.Player;

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
