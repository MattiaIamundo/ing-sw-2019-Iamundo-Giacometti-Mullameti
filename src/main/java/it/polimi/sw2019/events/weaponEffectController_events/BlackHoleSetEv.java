package it.polimi.sw2019.events.weaponEffectController_events;

/**
 * These class represent the set event of Black hole, The optional effect of Vortex Cannon
 */
public class BlackHoleSetEv {
    private String target1;
    private String target2;

    /**
     * @param target1 is the first player selected as target, must be one of those contained in the valid list on the choose event
     * @param target2 is the second player selected as target, must be one of those contained in the valid list on the choose event, could be null
     */
    public BlackHoleSetEv(String target1, String target2) {
        this.target1 = target1;
        this.target2 = target2;
    }

    public BlackHoleSetEv(String target1) {
        this.target1 = target1;
        target2 = null;
    }

    public String getTarget1() {
        return target1;
    }

    public String getTarget2() {
        return target2;
    }
}
