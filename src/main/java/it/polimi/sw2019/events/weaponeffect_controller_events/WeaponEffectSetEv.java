package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.events.ActionEv;

public abstract class WeaponEffectSetEv implements ActionEv {
    protected String playerNickname;

    @Override
    public String getPlayerNickname() {
        return playerNickname;
    }

    @Override
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }
}
