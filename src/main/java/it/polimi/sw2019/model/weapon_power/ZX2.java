package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.ZX2ChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the basic effect of Zx-2
 * @author Mattia Iamundo
 */
public class ZX2 extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        super.usePower(attacker,1);
        target.getPlance().setMark(attacker);
        target.getPlance().setMark(attacker);
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
