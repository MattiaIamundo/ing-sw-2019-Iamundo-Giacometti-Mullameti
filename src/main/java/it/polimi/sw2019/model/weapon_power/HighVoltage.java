package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.events.weaponeffect_controller_events.HighVoltageChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the second optional effect of T.H.O.R.
 * @author Mattia Iamundo
 */
public class HighVoltage extends SingleTarget implements Power{
    @Override
    public void usePower(Player attacker){
        super.usePower(attacker,2);
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
