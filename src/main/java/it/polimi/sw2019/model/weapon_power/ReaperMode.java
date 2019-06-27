package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;

/**
 * This class implements the alternative effect of Electroscythe
 * @author Mattia Iamundo
 */
public class ReaperMode implements Power{

    private ArrayList<Player> targets;

    @Override
    public void usePower(Player attacker){
        for (Player player : targets){
            player.getPlance().giveDamage(attacker,2);
        }
    }

    public void setTargets(ArrayList<Player> targets) {
        this.targets = targets;
    }

    public ArrayList<Player> getTargets() {
        return targets;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
