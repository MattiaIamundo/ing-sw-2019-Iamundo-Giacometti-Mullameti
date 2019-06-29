package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class represent the set event of Lock Rifle, the basic effect of Lock Rifle
 */
public class LockRifleSetEv implements TargetSetEv{
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of the choose event
     */
    public LockRifleSetEv(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }
}
