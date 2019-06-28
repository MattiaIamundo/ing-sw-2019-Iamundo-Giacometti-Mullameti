package it.polimi.sw2019.events.weaponEffectController_events;

/**
 * These class represent the set event of Power glove, the basic effect of Power Glove
 */
public class PowerGloveSetEv implements TargetSetEv{
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of choose event
     */
    public PowerGloveSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
