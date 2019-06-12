package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.events.GrenadeLaunchChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the basic effect for GrenadeLauncher
 * @author Mattia Iamundo
 */
public class GrenadeLauncher extends Observable<GrenadeLaunchChooseEv> implements Power, SingleTarget{

    private Player target = null;
    private boolean ismoved;
    private Space moveto = null;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 1);
        if (moveto != null){
            target.setPosition(moveto);
            ismoved = true;
        }else {
            ismoved = false;
        }
    }

    public void chooseTarget(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable, ArrayList<String> moveto) {
        notify(new GrenadeLaunchChooseEv(attacker, valid, notreachable, moveto));
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
}
