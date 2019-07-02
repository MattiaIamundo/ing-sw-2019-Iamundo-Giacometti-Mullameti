package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponeffect_controller_events.ExtraGrenadeChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the optional effect of Grenade Launcher
 * @author Mattia Iamundo
 */
public class ExtraGrenade implements Power{

    private ArrayList<Player> players;

    @Override
    public void usePower(Player attacker){
        for (Player player : players){
            player.getPlance().giveDamage(attacker,1);
            player.getPlance().removeMark(attacker);
        }
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void moveTarget(Space moveto, Player target){
        target.setPosition(moveto);
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
