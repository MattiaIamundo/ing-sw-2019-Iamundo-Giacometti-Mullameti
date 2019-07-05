package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the first optional effect of Plasma Gun
 * @author Mattia Iamundo
 */
public class PhaseGlide extends WithMove implements Power{

    @Override
    public void usePower(Player attacker){
        super.moveAttacker(attacker);
    }

}
