package it.polimi.sw2019.events.weaponeffect_controller_events;

public class ShotgunSetEv implements TargetSetEv{

    private String target;
    private String moveto;

    public ShotgunSetEv(String target, String moveto) {
        this.target = target;
        this.moveto = moveto;
    }

    @Override
    public String getTarget() {
        return target;
    }

    public String getMoveto() {
        return moveto;
    }
}
