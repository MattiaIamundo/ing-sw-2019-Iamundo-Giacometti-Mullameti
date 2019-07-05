package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implement the first optional effect of Cyberblade
 * @author Mattia Iamundo
 */
public class Shadowstep extends WithMove implements Power{

    @Override
    public void usePower(Player attacker){
        super.moveAttacker(attacker);
    }

}
