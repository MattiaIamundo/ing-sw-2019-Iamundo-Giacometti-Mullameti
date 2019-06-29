package it.polimi.sw2019.events.powerup_events;

public class TagbackGrenadeChooseEv implements PowerUpChooseEv{
    private String user;
    private String target;

    public TagbackGrenadeChooseEv(String user, String target) {
        this.user = user;
        this.target = target;
    }

    @Override
    public String getUser() {
        return user;
    }

    public String getTarget() {
        return target;
    }
}
