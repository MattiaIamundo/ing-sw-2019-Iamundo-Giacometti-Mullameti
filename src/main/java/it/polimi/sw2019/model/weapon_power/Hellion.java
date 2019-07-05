package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * This class implements the basic effect of Hellion
 * @author Mattia Iamundo
 */
public class Hellion extends SingleTarget implements Power{
    private ArrayList<Player> markTargets = new ArrayList<>();

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,1);
        for (Player player : markTargets){
            player.getPlance().setMark(attacker);
        }
    }

    public ArrayList<Player> getMarkTargets() {
        return markTargets;
    }

    public void setMarkTargets(ArrayList<Player> markTargets) {
        this.markTargets = markTargets;
    }

}
