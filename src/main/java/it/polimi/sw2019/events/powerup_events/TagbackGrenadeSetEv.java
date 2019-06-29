package it.polimi.sw2019.events.powerup_events;

public class TagbackGrenadeSetEv {
    private Boolean markTarget;

    public TagbackGrenadeSetEv(Boolean markTarget) {
        this.markTarget = markTarget;
    }

    public Boolean getMarkTarget() {
        return markTarget;
    }
}
