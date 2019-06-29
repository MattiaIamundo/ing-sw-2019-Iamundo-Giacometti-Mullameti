package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.events.weaponeffect_controller_events.SliceAndDiceChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the optional effect of Cyberblade
 * @author Mattia Iamundo
 */
public class SliceAndDice extends Observable<SliceAndDiceChooseEv> implements Power{

    private Player target;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 2);
        target.getPlance().removeMark(attacker);
    }

    public void chooseTarget(ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable, Player attacker){
        notify(new SliceAndDiceChooseEv(attacker, valid, notselectable, notreachable));
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
