package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * These class represent the set event of Grenade launcher, the basic effect of Grenade Launcher
 */
public class GrenadeLaunchSetEv implements TargetSetEv{
    private String target;
    private String moveto;

    /**
     * @param target is the player selected as target, must be one of those contained in the valid list of the choose event
     * @param moveto is the direction in which the attacker intend to move the target, must be one of those contained in the list of the direction associated to the chosen
     *               target in the hash map moveto in the choose event, must be null if the attacker doesn't want to move the target
     */
    public GrenadeLaunchSetEv(String target, String moveto) {
        this.target = target;
        this.moveto = moveto;
    }

    public GrenadeLaunchSetEv(String target) {
        this.target = target;
        moveto = null;
    }

    public String getTarget() {
        return target;
    }

    public String getMoveto() {
        return moveto;
    }
}
