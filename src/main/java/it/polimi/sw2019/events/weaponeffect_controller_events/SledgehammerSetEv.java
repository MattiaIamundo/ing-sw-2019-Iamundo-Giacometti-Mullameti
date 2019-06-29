package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class represent the choose event of Sledgehammer, the basic effect of Sledgehammer
 */
public class SledgehammerSetEv implements TargetSetEv{
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list in the choose event
     */
    public SledgehammerSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
