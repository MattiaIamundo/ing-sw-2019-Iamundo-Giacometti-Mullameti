package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.HellionChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the basic effect of Hellion
 * @author Mattia Iamundo
 */
public class Hellion extends Observable<HellionChooseEv> implements Power, SingleTarget{

    private Player target;
    private ArrayList<Player> markTargets = new ArrayList<>();

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 1);
        target.getPlance().removeMark(attacker);
        for (Player player : markTargets){
            player.getPlance().setMark(attacker);
        }
    }

    public void chooseTarget(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable){
        notify(new HellionChooseEv(attacker, valid, notreachable));
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    public ArrayList<Player> getMarkTargets() {
        return markTargets;
    }

    public void setMarkTargets(ArrayList<Player> markTargets) {
        this.markTargets = markTargets;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
