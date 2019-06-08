package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Events.TargetAcquisitionEv;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * This class implements the second optional effect of T.H.O.R.
 * @author Mattia Iamundo
 */
public class HighVoltage extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 2);
    }

    @Override
    public void chooseTarget(ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable, Player attacker) {
        notify(new TargetAcquisitionEv(attacker, valid, notselectable, notreachable, "Select a player that "+notselectable.get(1)+" can see"));
    }

    public Player getTarget(){
        return target;
    }
}
