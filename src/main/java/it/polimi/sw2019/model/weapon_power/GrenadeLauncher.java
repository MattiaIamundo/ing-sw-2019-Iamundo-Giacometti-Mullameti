package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponeffect_controller_events.GrenadeLaunchChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the basic effect for GrenadeLauncher
 * @author Mattia Iamundo
 */
public class GrenadeLauncher implements Power, SingleTarget{

    private Player target;
    private boolean ismoved;
    private Space moveto;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 1);
        if (moveto != null){
            target.setPosition(moveto);
            ismoved = true;
        }else {
            ismoved = false;
        }
        target.getPlance().removeMark(attacker);
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    public void setMoveto(Space moveto) {
        this.moveto = moveto;
    }

    public boolean isIsmoved() {
        return ismoved;
    }

    public Player getTarget() {
        return target;
    }

    public Space getMoveto() {
        return moveto;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
