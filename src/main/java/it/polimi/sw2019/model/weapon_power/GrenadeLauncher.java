package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

/**
 * This class implements the basic effect for GrenadeLauncher
 * @author Mattia Iamundo
 */
public class GrenadeLauncher extends SingleTarget implements Power{
    private boolean ismoved;
    private Space moveto;

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,1);
        if (moveto != null){
            target.setPosition(moveto);
            ismoved = true;
        }else {
            ismoved = false;
        }
    }

    public void setMoveto(Space moveto) {
        this.moveto = moveto;
    }

    public boolean isIsmoved() {
        return ismoved;
    }

    public Space getMoveto() {
        return moveto;
    }
}
