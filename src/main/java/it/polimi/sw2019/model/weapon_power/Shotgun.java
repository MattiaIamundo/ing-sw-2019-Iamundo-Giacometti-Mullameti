package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.events.weaponEffectController_events.ShotgunChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the basic effect of Shotgun
 * @author Mattia Iamundo
 */
public class Shotgun extends Observable<ShotgunChooseEv> implements Power, SingleTarget{
    private Player target;
    private Space moveto;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 3);
        target.getPlance().removeMark(attacker);
        if (moveto != null){
            target.setPosition(moveto);
        }
    }

    public void chooseTarget(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable, ArrayList<String> moveto){
        notify(new ShotgunChooseEv(attacker, valid, notreachable, moveto));
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    public Space getMoveto() {
        return moveto;
    }

    public void setMoveto(Space moveto) {
        this.moveto = moveto;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
