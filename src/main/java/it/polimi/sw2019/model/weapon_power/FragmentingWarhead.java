package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;

/**
 * This class implements the second optional effect of Rocket Launcher
 * @author Mattia Iamundo
 */
public class FragmentingWarhead extends SingleTarget implements Power{
    private ArrayList<Player> players;

    @Override
    public void usePower(Player attacker){
        for (Player player : players){
            player.getPlance().giveDamage(attacker, 1);
            player.getPlance().removeMark(attacker);
        }
        if (target != null){
            super.giveDamage(attacker,1);
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

}
