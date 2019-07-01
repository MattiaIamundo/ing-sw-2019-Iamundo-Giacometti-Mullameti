package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.WhisperChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

public class Whisper implements Power, SingleTarget{
    //basic effect for WhisperCont
    private Player target;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker,3);
        target.getPlance().removeMark(attacker);
        target.getPlance().setMark(attacker);
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
