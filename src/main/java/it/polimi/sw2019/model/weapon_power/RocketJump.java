package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the first optional effect of Rocket Launcher
 * @author Mattia Iamundo
 */
public class RocketJump extends WithMove implements Power{

    @Override
    public void usePower(Player attacker){
        super.moveAttacker(attacker);
    }

}
