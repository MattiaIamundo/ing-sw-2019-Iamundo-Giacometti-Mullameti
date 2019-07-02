package it.polimi.sw2019.events.weaponeffect_controller_events;

import java.util.ArrayList;

public abstract class MovePlayerChooseEv implements WeaponEvent{
    protected String attacker;
    protected ArrayList<String> positions;

    public MovePlayerChooseEv(String attacker, ArrayList<String> positions) {
        this.attacker = attacker;
        this.positions = positions;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public ArrayList<String> getPositions() {
        return positions;
    }

}
