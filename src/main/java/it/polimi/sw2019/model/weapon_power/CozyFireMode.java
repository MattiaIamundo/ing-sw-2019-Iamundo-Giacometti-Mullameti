package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * This class implements the alternative power of the Furnace
 * @author Mattia Iamundo
 */
public class CozyFireMode implements Power{

    private ArrayList<Player> players;

    @Override
    public void usePower(Player attacker){
        for (Player player : players){
            player.getPlance().giveDamage(attacker,1);
            player.getPlance().removeMark(attacker);
            player.getPlance().setMark(attacker);
        }
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
