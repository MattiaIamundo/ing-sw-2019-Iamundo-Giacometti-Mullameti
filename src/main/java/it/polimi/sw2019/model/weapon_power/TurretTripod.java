package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponEffectController_events.TurretTripodChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the second optional effect of Machine Gun
 * @author Mattia Iamundo
 */
public class TurretTripod extends Observable<TurretTripodChooseEv> implements Power, SingleTarget{

    private Player target;
    private Player previoustarget;

    @Override
    public void usePower(Player attacker){
        previoustarget.getPlance().giveDamage(attacker, 1);
        previoustarget.getPlance().removeMark(attacker);
        if (target != null){
            target.getPlance().giveDamage(attacker,1);
            target.getPlance().removeMark(attacker);
        }
    }

    public void chooseTarget(Player attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable){
        notify(new TurretTripodChooseEv(attacker, valid, notselectable, notreachable));
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    public Player getPrevioustarget() {
        return previoustarget;
    }

    public void setPrevioustarget(Player previoustarget) {
        this.previoustarget = previoustarget;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
