package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class represent the set event of Nano tracer mode, the alternative effect of Hellion
 */
public class NanoTracerSetEv implements TargetSetEv{
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of choose event
     */
    public NanoTracerSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
