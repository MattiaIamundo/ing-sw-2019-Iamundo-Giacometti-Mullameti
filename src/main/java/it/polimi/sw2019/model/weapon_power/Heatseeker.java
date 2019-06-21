package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.events.HeatseekerChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the basic effect of Heatseeker
 * @author Mattia Iamundo
 */
public class Heatseeker extends Observable<HeatseekerChooseEv> implements Power, SingleTarget{

    private Player target;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 3);
    }

    public void chooseTarget(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable){
        notify(new HeatseekerChooseEv(attacker, valid, notreachable));
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
