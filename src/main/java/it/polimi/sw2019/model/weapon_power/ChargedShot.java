package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implement the optional power for the PlasmaGun
 * @author Mattia Iamundo
 */
public class ChargedShot implements Power{

    private Player target;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker,1);
    }

    public void setTarget(Player target) {
        this.target = target;
    }
}
