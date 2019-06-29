package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class represent the choose event of Shotgun, the basic effect of Shotgun
 */
public class ShotgunSetEv implements TargetSetEv{
    private String target;
    private String moveto;

    /**
     * @param target is the player selected as target, must be one those contained in the valid list of the choose event
     * @param moveto is the direction in which the attacker want to move the target, must be one of those contained in the moveto list of choose event,
     *               can be null if no movement is required
     */
    public ShotgunSetEv(String target, String moveto) {
        this.target = target;
        this.moveto = moveto;
    }

    @Override
    public String getTarget() {
        return target;
    }

    public String getMoveto() {
        return moveto;
    }
}
