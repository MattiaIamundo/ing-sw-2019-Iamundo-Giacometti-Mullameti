package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.events.TargetAcquisitionEv;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * This class implements the basic effect of T.H.O.R. and Plasma Gun
 * @author Mattia Iamundo
 */
public class TwoDamage extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 2);
    }

    @Override
    public void chooseTarget(ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable, Player attacker) {
        notify(new TargetAcquisitionEv(attacker, valid, notselectable, notreachable, "Select a player that you can see"));
    }

    public Player getTarget(){
        return target;
    }
}
