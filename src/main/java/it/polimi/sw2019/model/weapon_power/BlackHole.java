package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.events.BlackHoleChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the optional effect for the Vortex Cannon
 * @author Mattia Iamundo
 */
public class BlackHole extends Observable<BlackHoleChooseEv> implements Power{

    private Player target1;
    private Player target2;
    private Space vortex;

    @Override
    public void usePower(Player attacker){
        target1.setPosition(vortex);
        target1.getPlance().giveDamage(attacker, 1);
        if (target2 != null){
            target2.setPosition(vortex);
            target2.getPlance().giveDamage(attacker, 1);
        }
    }

    public void chooseTargets(ArrayList<String> targets, String notselectable, Player attacker){
        notify(new BlackHoleChooseEv(targets, notselectable, attacker));
    }

    public void setTarget1(Player target1) {
        this.target1 = target1;
    }

    public void setTarget2(Player target2) {
        this.target2 = target2;
    }

    public void setVortex(Space vortex) {
        this.vortex = vortex;
    }
}
