package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the basic effect of Zx-2
 * @author Mattia Iamundo
 */
public class ZX2 extends SingleTarget implements Power{

    private Player target;

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,1);
        super.setMark(attacker);
        super.setMark(attacker);
    }
}
