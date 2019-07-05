package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the second optional effect of T.H.O.R.
 * @author Mattia Iamundo
 */
public class HighVoltage extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,2);
    }
}
