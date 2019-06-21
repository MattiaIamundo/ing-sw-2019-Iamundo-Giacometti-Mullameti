package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.events.RailGunChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the basic effect of Railgun
 * @author Mattia Iamundo
 */
public class RailGun extends Observable<RailGunChooseEv> implements Power, ThroughWalls{
    private Player target = null;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 3);
        target = null;
    }

    public void chooseTarget(HashMap<String, ArrayList<String>> valid, Player attacker){
        notify(new RailGunChooseEv(valid, attacker));
    }

    public void setTarget(Player target){
        this.target = target;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
