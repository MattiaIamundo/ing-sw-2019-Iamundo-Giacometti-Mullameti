package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.MachineGunChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implement the basic effect of Machine Gun
 * @author Mattia Iamundo
 */
public class MachineGun extends SingleTarget implements Power{

    private Player target2;

    @Override
    public void usePower(Player attacker){
        super.usePower(attacker, 1);
        if (target2 != null){
            target2.getPlance().giveDamage(attacker,1);
            target2.getPlance().removeMark(attacker);
        }
    }

    public Player getTarget2() {
        return target2;
    }

    public void setTarget2(Player target2) {
        this.target2 = target2;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
