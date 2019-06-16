package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.events.PiercingModeChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements the alternative effect of Railgun
 * @author Mattia Iamundo
 */
public class PiercingMode extends Observable<PiercingModeChooseEv> implements Power, ThroughWalls{

    private Player target1 = null;
    private Player target2 = null;

    @Override
    public void usePower(Player attacker){
        target1.getPlance().giveDamage(attacker, 2);
        if (target2 != null){
            target2.getPlance().giveDamage(attacker, 2);
        }
    }

    public void chooseTarget(HashMap<String, ArrayList<String>> targets, Player attacker){
        notify(new PiercingModeChooseEv(targets, attacker));
    }

    public void setTarget1(Player target1) {
        this.target1 = target1;
    }

    public void setTarget2(Player target2) {
        this.target2 = target2;
    }
}
