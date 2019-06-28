package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.events.weaponEffectController_events.ExtraGrenadeChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the optional effect of Grenade Launcher
 * @author Mattia Iamundo
 */
public class ExtraGrenade extends Observable<ExtraGrenadeChooseEv> implements Power{

    private ArrayList<Player> players;

    @Override
    public void usePower(Player attacker){
        for (Player player : players){
            player.getPlance().giveDamage(attacker,1);
            player.getPlance().removeMark(attacker);
        }
    }

    public void chooseSquare(Player attacker, ArrayList<String> validsquare){
        notify(new ExtraGrenadeChooseEv(attacker, validsquare));
    }

    public void chooseSquare(Player attacker, ArrayList<String> validsquare, ArrayList<String> moveto){
        notify(new ExtraGrenadeChooseEv(attacker, validsquare, moveto));
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
