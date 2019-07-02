package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.events.weaponeffect_controller_events.PulvModeChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the alternative effect of Sledgehammer
 * @author Mattia Iamundo
 */
public class PulverizeMode extends Observable<PulvModeChooseEv> implements Power{
    private Space moveto;
    private Player target;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 3);
        target.getPlance().removeMark(attacker);
        target.setPosition(moveto);
    }

    public void chooseTarget(ArrayList<String> targets, ArrayList<String> positions, Player attacker){
        notify(new PulvModeChooseEv(targets, positions, attacker));
    }

    public Space getMoveto() {
        return moveto;
    }

    public void setMoveto(Space moveto) {
        this.moveto = moveto;
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
