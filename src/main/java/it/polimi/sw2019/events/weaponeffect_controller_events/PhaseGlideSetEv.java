package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class represent the set event of Phase glide, the first optional effect of Plasma Gun
 */
public class PhaseGlideSetEv implements MoveTargetSetEv{
    private String moveto;

    /**
     * @param moveto is the moving direction chosen by the attacker, must be one of those contained in the positions list in the choose event
     */
    public PhaseGlideSetEv(String moveto) {
        this.moveto = moveto;
    }

    @Override
    public String getMoveto() {
        return moveto;
    }
}
