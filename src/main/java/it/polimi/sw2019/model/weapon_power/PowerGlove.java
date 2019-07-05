package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the basic effect of Power Glove
 * @author Mattia Iamundo
 */
public class PowerGlove extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        attacker.setPosition(target.getPosition());
        super.giveDamage(attacker,1);
        super.setMark(attacker);
        super.setMark(attacker);
    }

}
