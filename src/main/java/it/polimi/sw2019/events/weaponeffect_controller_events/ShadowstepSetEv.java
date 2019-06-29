package it.polimi.sw2019.events.weaponeffect_controller_events;

public class ShadowstepSetEv implements MoveTargetSetEv{

    private String moveto;

    public ShadowstepSetEv(String moveto) {
        this.moveto = moveto;
    }

    @Override
    public String getMoveto() {
        return moveto;
    }
}
