package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class represent the set event of Rocket jump, the first optional effect of Rocket Launcher
 */
public class RocketJumpSetEv implements MoveTargetSetEv{
    private String moveto;

    /**
     * @param moveto is the square in which the attacker intend to move, must be one of those contained in the positions list oin the choose event
     */
    public RocketJumpSetEv(String moveto) {
        this.moveto = moveto;
    }

    @Override
    public String getMoveto() {
        return moveto;
    }
}
