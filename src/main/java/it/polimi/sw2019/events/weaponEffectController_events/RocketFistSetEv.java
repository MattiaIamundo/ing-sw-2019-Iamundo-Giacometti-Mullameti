package it.polimi.sw2019.events.weaponEffectController_events;

public class RocketFistSetEv implements LineFireSetEv{

    private String direction;
    private String target1;
    private String target2;

    public RocketFistSetEv(String direction, String target1, String target2) {
        this.direction = direction;
        this.target1 = target1;
        this.target2 = target2;
    }

    public String getDirection() {
        return direction;
    }

    public String getTarget1() {
        return target1;
    }

    public String getTarget2() {
        return target2;
    }
}
