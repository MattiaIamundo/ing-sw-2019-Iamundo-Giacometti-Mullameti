package it.polimi.sw2019.model.events;

public class RocketJumpSetEv implements MoveTargetSetEv{

    private String moveto;

    public RocketJumpSetEv(String moveto) {
        this.moveto = moveto;
    }

    @Override
    public String getMoveto() {
        return moveto;
    }
}
