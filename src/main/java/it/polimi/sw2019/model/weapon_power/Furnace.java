package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * This class implement the basic effect of Furnace
 * @author Mattia Iamundo
 */
public class Furnace implements Power{

    private ArrayList<Player> targets;

    @Override
    public void usePower(Player attacker){
        for (Player player : targets){
            player.getPlance().giveDamage(attacker,1);
            player.getPlance().removeMark(attacker);
        }
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
