package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.LockRifleChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the basic effect of Lock Rifle
 * @author Mattia Iamundo
 */
public class LockRifle extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        super.usePower(attacker,2);
        target.getPlance().setMark(attacker);
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
