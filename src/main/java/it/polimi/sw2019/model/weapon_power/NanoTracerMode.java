package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * This class implements the alternative effect of Hellion
 * @author Mattia Iamundo
 */
public class NanoTracerMode extends SingleTarget implements Power{
    private ArrayList<Player> targets;

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,1);
        for (Player player : targets){
            player.getPlance().setMark(attacker);
            player.getPlance().setMark(attacker);
        }
    }

    public ArrayList<Player> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<Player> targets) {
        this.targets = targets;
    }

}
