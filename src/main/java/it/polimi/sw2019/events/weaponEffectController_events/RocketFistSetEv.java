package it.polimi.sw2019.events.weaponEffectController_events;

public class RocketFistSetEv implements LineFireSetEv{

    private String direction;
    private int moveAmount;
    private String target1;
    private String target2;

    public RocketFistSetEv(String direction, int moveAmount, String target1, String target2) {
        this.direction = direction;
        this.moveAmount = moveAmount;
        this.target1 = target1;
        this.target2 = target2;
    }

    public String getDirection() {
        return direction;
    }

    public int getMoveAmount() {
        return moveAmount;
    }

    public String getTarget1() {
        return target1;
    }

    public String getTarget2() {
        return target2;
    }
}
