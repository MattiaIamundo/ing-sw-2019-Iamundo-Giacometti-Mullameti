package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.events.weaponEffectController_events.FurnaceChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implement the basic effect of Furnace
 * @author Mattia Iamundo
 */
public class Furnace extends Observable<FurnaceChooseEv> implements Power{

    private ArrayList<Player> targets;

    @Override
    public void usePower(Player attacker){
        for (Player player : targets){
            player.getPlance().giveDamage(attacker,1);
        }
    }

    public void chooseRoom(Player attacker, ArrayList<String> rooms){
        notify(new FurnaceChooseEv(attacker, rooms));
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
