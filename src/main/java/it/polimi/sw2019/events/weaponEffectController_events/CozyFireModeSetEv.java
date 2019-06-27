package it.polimi.sw2019.events.weaponEffectController_events;

/**
 * These class represent the set event of Cozy fire mode, the alternative effect of Furnace
 */
public class CozyFireModeSetEv {
    private String direction;

    /**
     * @param direction is the cardinal direction of the square selected as the target, must be one of those contained in the list of the choose event
     */
    public CozyFireModeSetEv(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}
