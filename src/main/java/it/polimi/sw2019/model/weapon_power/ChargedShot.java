package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implement the optional power for the PlasmaGun
 * @author Mattia Iamundo
 */
public class ChargedShot implements Power{
    private TwoDamage basiceffect;
    private int i = 0;
    @Override
    public void usePower(Player attacker){
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("PlasmaGun"))){
            i++;
        }
        basiceffect = (TwoDamage) attacker.listWeapon()[i].getPower();
        basiceffect.target.getPlance().giveDamage(attacker, 1);
    }
}
