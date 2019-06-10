package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.events.TargetAcquisitionEv;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * This class implements the alternative effect of Tractor Beam
 * @author Mattia Iamundo
 */
public class PunisherMode extends SingleTarget implements Power{

    private Player target;

    @Override
    public void usePower(Player attacker){
        target.setPosition(attacker.getPosition());
        target.getPlance().giveDamage(attacker, 3);
    }

    @Override
    public void chooseTarget(ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable, Player attacker) {
        notify(new TargetAcquisitionEv(attacker, valid, notselectable, notreachable, "Select a player that is at most 2 moves away from you"));
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }
}
