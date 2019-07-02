package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implement the optional power for the PlasmaGun
 * @author Mattia Iamundo
 */
public class ChargedShot extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,1);
    }
}
