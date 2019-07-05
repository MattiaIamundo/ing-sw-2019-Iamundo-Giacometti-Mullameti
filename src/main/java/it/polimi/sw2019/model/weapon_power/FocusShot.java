package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the first optional effect of Machine Gun
 * @author Mattia Iamundo
 */
public class FocusShot extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,1);
    }
}
