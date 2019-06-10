package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.events.ChainReactChooseEv;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * This class implements the first optional power for the T.H.O.R.
 * @author Mattia Iamundo
 */
public class ChainReaction extends SingleTarget implements Power {

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 1);
    }

    @Override
    public void chooseTarget(ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable, Player attacker) {
        notify(new ChainReactChooseEv(attacker, valid, notselectable, notreachable, "Select a player that "+notselectable.get(1)+"can see"));
    }

    public Player getTarget(){
        return target;
    }
}
