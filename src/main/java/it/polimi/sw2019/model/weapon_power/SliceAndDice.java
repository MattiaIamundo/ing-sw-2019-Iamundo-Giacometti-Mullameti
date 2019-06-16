package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.events.SliceAndDiceChooseEv;
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
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public void chooseTarget(ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable, Player attacker){
        notify(new SliceAndDiceChooseEv(attacker, valid, notselectable, notreachable));
    }
}
