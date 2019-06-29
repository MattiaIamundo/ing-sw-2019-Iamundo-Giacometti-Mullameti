package it.polimi.sw2019.events.weaponeffect_controller_events;

/**
 * This class represent the select event for Barbecue mode, the alternative effect of Flamethrower
 */
public class BarbecueSetEv {
    private String direction;

    /**
     * @param direction the cardinal direction selected by the attacker, must be one of those contained in the list of the choose event
     */
    public BarbecueSetEv(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}
