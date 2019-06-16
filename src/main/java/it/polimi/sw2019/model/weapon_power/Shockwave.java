package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.events.ShockwaveChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * This class implements the basic effect of Shockwave
 * @author Mattia Iamundo
 */
public class Shockwave extends Observable<ShockwaveChooseEv> implements Power{

    private Player target1;
    private Player target2;
    private Player target3;

    @Override
    public void usePower(Player attacker){
        target1.getPlance().giveDamage(attacker, 1);
        if (target2 != null){
            target2.getPlance().giveDamage(attacker, 1);
            if (target3 != null){
                target3.getPlance().giveDamage(attacker,1);
            }
        }
    }

    public void chooseTargets(Player attacker, HashMap<String, ArrayList<String>> targets){
        notify(new ShockwaveChooseEv(attacker, targets));
    }

    public void setTarget1(Player target1) {
        this.target1 = target1;
    }

    public void setTarget2(Player target2) {
        this.target2 = target2;
    }

    public void setTarget3(Player target3) {
        this.target3 = target3;
    }
}
