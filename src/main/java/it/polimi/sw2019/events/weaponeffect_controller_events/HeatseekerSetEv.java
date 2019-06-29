package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class is the set event of Heatseeker, the basic effect of Heatseeker
 */
public class HeatseekerSetEv implements TargetSetEv{
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of the choose event
     */
    public HeatseekerSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
