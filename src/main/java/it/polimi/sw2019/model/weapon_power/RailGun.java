package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the basic effect of Railgun
 * @author Mattia Iamundo
 */
public class RailGun extends SingleTarget implements Power, ThroughWalls{

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,3);
    }

}
