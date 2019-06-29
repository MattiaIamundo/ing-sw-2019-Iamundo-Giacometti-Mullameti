package it.polimi.sw2019.events.powerup_events;

public class NewtonSetEv {
    private String target;
    private String moveto;

    public NewtonSetEv(String target, String moveto) {
        this.target = target;
        this.moveto = moveto;
    }

    public String getTarget() {
        return target;
    }

    public String getMoveto() {
        return moveto;
    }
}
