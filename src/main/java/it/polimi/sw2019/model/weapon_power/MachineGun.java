package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.events.MachineGunChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implement the basic effect of Machine Gun
 * @author Mattia Iamundo
 */
public class MachineGun extends Observable<MachineGunChooseEv> implements Power, SingleTarget{

    private Player target1;
    private Player target2;

    @Override
    public void usePower(Player attacker){
        target1.getPlance().giveDamage(attacker,1);
        if (target2 != null){
            target2.getPlance().giveDamage(attacker,1);
        }
    }

    public void chooseTarget(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable){
        notify(new MachineGunChooseEv(attacker, valid, notreachable));
    }

    public Player getTarget1() {
        return target1;
    }

    public Player getTarget2() {
        return target2;
    }

    public void setTarget(Player target1) {
        this.target1 = target1;
    }

    public void setTarget2(Player target2) {
        this.target2 = target2;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}