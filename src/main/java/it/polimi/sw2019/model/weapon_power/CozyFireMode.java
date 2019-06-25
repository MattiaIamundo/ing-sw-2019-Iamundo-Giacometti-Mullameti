package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.events.weaponEffectController_events.CozyFireModeChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the alternative power of the Furnace
 * @author Mattia Iamundo
 */
public class CozyFireMode extends Observable<CozyFireModeChooseEv> implements Power{

    private ArrayList<Player> players;

    @Override
    public void usePower(Player attacker){
        for (Player player : players){
            player.getPlance().giveDamage(attacker,1);
            player.getPlance().removeMark(attacker);
            player.getPlance().setMark(attacker);
        }
    }

    public void chooseTargetArea(Player attacker, ArrayList<String> directions){
        notify(new CozyFireModeChooseEv(attacker, directions));
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
