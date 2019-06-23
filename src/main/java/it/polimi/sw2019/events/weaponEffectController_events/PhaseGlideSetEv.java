package it.polimi.sw2019.events.weaponEffectController_events;

public class PhaseGlideSetEv implements MoveTargetSetEv{

    private String moveto;

    public PhaseGlideSetEv(String moveto) {
        this.moveto = moveto;
    }

    @Override
    public String getMoveto() {
        return moveto;
    }
}
