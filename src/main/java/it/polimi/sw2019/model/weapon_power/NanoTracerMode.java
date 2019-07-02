package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.NanoTracerChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the alternative effect of Hellion
 * @author Mattia Iamundo
 */
public class NanoTracerMode implements Power, SingleTarget{

    private Player target;
    private ArrayList<Player> targets;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 1);
        target.getPlance().removeMark(attacker);
        for (Player player : targets){
            player.getPlance().setMark(attacker);
            player.getPlance().setMark(attacker);
        }
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    public ArrayList<Player> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<Player> targets) {
        this.targets = targets;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
