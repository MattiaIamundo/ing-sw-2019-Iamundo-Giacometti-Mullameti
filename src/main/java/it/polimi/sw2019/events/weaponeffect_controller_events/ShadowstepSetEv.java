package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class represent the set event of Shadowstep, the first optional effect of Cyberblade
 */
public class ShadowstepSetEv implements MoveTargetSetEv{
    private String moveto;

    /**
     * @param moveto is the direction in which the attacker chose to move in, must be one of those contained in the positions list in the choose event
     */
    public ShadowstepSetEv(String moveto) {
        this.moveto = moveto;
    }

    @Override
    public String getMoveto() {
        return moveto;
    }
}
