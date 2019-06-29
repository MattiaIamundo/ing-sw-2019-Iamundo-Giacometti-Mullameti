package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class represent the set event of Long barrel mode, the alternative effect of Shotgun
 */
public class LongBarrelSetEv implements TargetSetEv{
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of choose event
     */
    public LongBarrelSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
