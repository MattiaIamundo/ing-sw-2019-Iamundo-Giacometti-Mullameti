package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.events.weaponeffect_controller_events.ChainReactChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the first optional power for the T.H.O.R.
 * @author Mattia Iamundo
 */
public class ChainReaction extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        super.usePower(attacker,1);
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
