package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Events.PulvModeChooseEv;
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
        target.setPosition(moveto);
    }

    public void chooseTarget(ArrayList<String> targets, ArrayList<String> positions){
        notify(new PulvModeChooseEv(targets, positions));
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public void setMoveto(Space moveto) {
        this.moveto = moveto;
    }
}
