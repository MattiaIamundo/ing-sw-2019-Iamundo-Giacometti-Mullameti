package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.events.PunisherModeChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the alternative effect of Tractor Beam
 * @author Mattia Iamundo
 */
public class PunisherMode extends Observable<PunisherModeChooseEv> implements Power, SingleTarget{

    private Player target;

    @Override
    public void usePower(Player attacker){
        target.setPosition(attacker.getPosition());
        target.getPlance().giveDamage(attacker, 3);
    }

    //@Override
    public void chooseTarget(ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable, Player attacker) {
        notify(new PunisherModeChooseEv(attacker, valid, notselectable, notreachable));
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }
}
