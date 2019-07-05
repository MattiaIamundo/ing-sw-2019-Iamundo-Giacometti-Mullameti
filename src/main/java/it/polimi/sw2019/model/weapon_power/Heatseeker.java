package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the basic effect of Heatseeker
 * @author Mattia Iamundo
 */
public class Heatseeker extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,3);
    }
}
