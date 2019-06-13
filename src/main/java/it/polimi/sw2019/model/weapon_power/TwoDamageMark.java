package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.events.LockRifleChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the basic effect of Lock Rifle
 * @author Mattia Iamundo
 */
public class TwoDamageMark extends Observable<LockRifleChooseEv> implements Power, SingleTarget{

    private Player target;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 2);
        target.getPlance().setMark(attacker);
    }

    public void chooseTarget(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable){
        notify(new LockRifleChooseEv(attacker, valid, notreachable));
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }
}
