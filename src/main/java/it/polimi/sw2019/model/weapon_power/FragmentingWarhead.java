package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;

/**
 * This class implements the second optional effect of Rocket Launcher
 * @author Mattia Iamundo
 */
public class FragmentingWarhead implements Power{

    private Player target;
    private ArrayList<Player> players;

    @Override
    public void usePower(Player attacker){
        for (Player player : players){
            player.getPlance().giveDamage(attacker, 1);
            player.getPlance().removeMark(attacker);
        }
        if (target != null){
            target.getPlance().giveDamage(attacker,1);
            target.getPlance().removeMark(attacker);
        }
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
