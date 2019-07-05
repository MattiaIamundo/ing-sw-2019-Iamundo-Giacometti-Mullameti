package it.polimi.sw2019.events.powerup_events;

import it.polimi.sw2019.events.ActionEv;

public abstract class MovePlayerSetEvent implements ActionEv {
    protected String playerNickname;
    protected String target;
    protected String moveto;

    public MovePlayerSetEvent(String target, String moveto) {
        this.target = target;
        this.moveto = moveto;
    }

    @Override
    public String getPlayerNickname() {
        return playerNickname;
    }

    @Override
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public String getTarget() {
        return target;
    }

    public String getMoveto() {
        return moveto;
    }
}
