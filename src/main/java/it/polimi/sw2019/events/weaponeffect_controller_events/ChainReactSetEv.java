package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class is the set event of Chain reaction, the first optional effect of T.H.O.R.
 */
public class ChainReactSetEv implements TargetSetEv{
    private String target;

    /**
     * @param target is the selected target, must be one of those contained in the valid list of the choose event
     */
    public ChainReactSetEv(String target) {
        this.target = target;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
