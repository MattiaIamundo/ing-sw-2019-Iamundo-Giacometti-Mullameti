package it.polimi.sw2019.model.events;

public class MoveTargetEv {
    private String moveto;

    public MoveTargetEv(String moveto) {
        this.moveto = moveto;
    }

    public String getMoveto() {
        return moveto;
    }
}
