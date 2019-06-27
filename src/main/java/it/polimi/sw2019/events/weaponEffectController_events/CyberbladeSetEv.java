package it.polimi.sw2019.events.weaponEffectController_events;

/**
 * These class represent the set event of Cyberblade, the basic effect of Cyberblade
 */
public class CyberbladeSetEv implements TargetSetEv{
    private String target;

    /**
     * @param target is the player selected as the target of the effect, must be one of those contained in the valid list of the choose event
     */
    public CyberbladeSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
