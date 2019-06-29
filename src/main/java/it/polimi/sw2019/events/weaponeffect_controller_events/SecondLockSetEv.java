package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class represent the set event of Second lock, the optional effect of Lock Rifle
 */
public class SecondLockSetEv implements TargetSetEv{
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of the choose event
     */
    public SecondLockSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
