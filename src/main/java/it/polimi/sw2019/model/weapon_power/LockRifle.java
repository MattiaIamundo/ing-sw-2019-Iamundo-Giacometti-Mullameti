package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the basic effect of Lock Rifle
 * @author Mattia Iamundo
 */
public class LockRifle extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,2);
        target.getPlance().setMark(attacker);
    }
}
