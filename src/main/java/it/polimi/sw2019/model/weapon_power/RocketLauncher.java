package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.events.weaponeffect_controller_events.RocketLaunchChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the basic effect of Rocket Launcher
 * @author Mattia Iamundo
 */
public class RocketLauncher extends Observable<RocketLaunchChooseEv> implements Power{

    private Player target;
    private Space origin;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 2);
        target.getPlance().removeMark(attacker);
    }

    public void setTarget(Player target, Space newposition) {
        this.target = target;
        origin = target.getPosition();
        this.target.setPosition(newposition);
    }

    public void chooseTarget(HashMap<String, ArrayList<String>> targets, Player attacker){
        notify(new RocketLaunchChooseEv(targets, attacker));
    }

    public Player getTarget() {
        return target;
    }

    public Space getOrigin() {
        return origin;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
