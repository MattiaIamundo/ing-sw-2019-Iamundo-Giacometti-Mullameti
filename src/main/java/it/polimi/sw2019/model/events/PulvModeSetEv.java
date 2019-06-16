package it.polimi.sw2019.model.events;

public class PulvModeSetEv {
    private String target;
    private String moveto;

    public PulvModeSetEv(String target, String moveto) {
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
