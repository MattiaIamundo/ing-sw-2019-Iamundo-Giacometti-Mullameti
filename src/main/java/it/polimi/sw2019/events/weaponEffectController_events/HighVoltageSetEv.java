package it.polimi.sw2019.events.weaponEffectController_events;

/**
 * These class represent the set event of High voltage, the second optional effect of T.H.O.R.
 */
public class HighVoltageSetEv implements TargetSetEv{
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of the choose event
     */
    public HighVoltageSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
