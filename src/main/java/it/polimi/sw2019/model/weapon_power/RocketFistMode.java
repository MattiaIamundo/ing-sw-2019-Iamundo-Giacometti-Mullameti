package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponEffectController_events.RocketFistChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the alternative effect of PowerGlove
 * @author Mattia Iamundo
 */
public class RocketFistMode extends Observable<RocketFistChooseEv> implements Power, LineFire{

    private Space moveto;
    private Player target1;
    private Player target2;

    @Override
    public void usePower(Player attacker){
        attacker.setPosition(moveto);
        if (target1 != null) {
            target1.getPlance().giveDamage(attacker, 2);
        }
        if (target2 != null) {
            target2.getPlance().giveDamage(attacker, 2);
        }
    }

    public void chooseTarget(HashMap<String, ArrayList<String>> firstline, HashMap<String, ArrayList<String>> secondline, Player attacker){
        notify(new RocketFistChooseEv(attacker, firstline, secondline));
    }

    public Space getMoveto() {
        return moveto;
    }

    public void setMoveto(Space moveto) {
        this.moveto = moveto;
    }

    public Player getTarget1() {
        return target1;
    }

    @Override
    public void setTarget1(Player target1) {
        this.target1 = target1;
    }

    public Player getTarget2() {
        return target2;
    }

    @Override
    public void setTarget2(Player target2) {
        this.target2 = target2;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
