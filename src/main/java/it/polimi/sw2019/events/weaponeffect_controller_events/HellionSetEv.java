package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class represent the set event of Hellion, the basic effect of Hellion
 */
public class HellionSetEv implements TargetSetEv{
    private String target;

    /**
     * @param target is the player selected as target, it must be on of those contained in the valid list of the choose event
     */
    public HellionSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
