package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.SecondLockChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This method implements the optional effect of Lock Rifle
 * @author Mattia Iamundo
 */
public class SecondLock extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        super.setMark(attacker);
    }

}
