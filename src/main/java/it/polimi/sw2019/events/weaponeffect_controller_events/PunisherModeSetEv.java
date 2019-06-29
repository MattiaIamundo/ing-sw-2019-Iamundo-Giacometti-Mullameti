package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * these class represent the set event of Punisher mode, the alternative effect of Tractor beam
 */
public class PunisherModeSetEv implements TargetSetEv{
    private String target;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of the choose event
     */
    public PunisherModeSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
