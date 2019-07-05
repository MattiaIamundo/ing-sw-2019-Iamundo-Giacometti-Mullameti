package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

/**
 * This class implements the second optional effect of Machine Gun
 * @author Mattia Iamundo
 */
public class TurretTripod extends SingleTarget implements Power{
    private Player previoustarget;

    @Override
    public void usePower(Player attacker){
        previoustarget.getPlance().giveDamage(attacker, 1);
        previoustarget.getPlance().removeMark(attacker);
        if (target != null){
            super.giveDamage(attacker,1);
        }
    }

    public Player getPrevioustarget() {
        return previoustarget;
    }

    public void setPrevioustarget(Player previoustarget) {
        this.previoustarget = previoustarget;
    }

}
