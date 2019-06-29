package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.events.weaponeffect_controller_events.HighVoltageChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the second optional effect of T.H.O.R.
 * @author Mattia Iamundo
 */
public class HighVoltage extends Observable<HighVoltageChooseEv> implements Power, SingleTarget{

    private Player target;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 2);
        target.getPlance().removeMark(attacker);
    }

    public void chooseTarget(Player attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable) {
        notify(new HighVoltageChooseEv(attacker, valid, notselectable, notreachable));
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
