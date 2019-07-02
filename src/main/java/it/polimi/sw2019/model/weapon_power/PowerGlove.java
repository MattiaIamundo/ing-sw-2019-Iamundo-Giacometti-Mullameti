package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.PowerGloveChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the basic effect of Power Glove
 * @author Mattia Iamundo
 */
public class PowerGlove extends Observable<PowerGloveChooseEv> implements Power, SingleTarget{

    private Player target;

    @Override
    public void usePower(Player attacker){
        attacker.setPosition(target.getPosition());
        target.getPlance().giveDamage(attacker, 1);
        target.getPlance().removeMark(attacker);
        target.getPlance().setMark(attacker);
        target.getPlance().setMark(attacker);
    }

    public void chooseTarget(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable){
        notify(new PowerGloveChooseEv(attacker, valid, notreachable));
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
