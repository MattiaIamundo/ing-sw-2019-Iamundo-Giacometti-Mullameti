package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.ExtraGrenadeChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the optional effect of Grenade Launcher
 * @author Mattia Iamundo
 */
public class ExtraGrenade extends Observable<ExtraGrenadeChooseEv> implements Power{

    private Space targetarea;

    @Override
    public void usePower(Player attacker){
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition() == targetarea){
                Table.getPlayers(i).getPlance().giveDamage(attacker, 1);
            }
        }
    }

    public void chooseSquare(Player attacker, ArrayList<String> validsquare){
        notify(new ExtraGrenadeChooseEv(attacker, validsquare));
    }

    public void chooseSquare(Player attacker, ArrayList<String> validsquare, ArrayList<String> moveto){
        notify(new ExtraGrenadeChooseEv(attacker, validsquare, moveto));
    }

    public void setTargetarea(Space targetarea) {
        this.targetarea = targetarea;
    }

    public void moveTarget(Space moveto, Player target){
        target.setPosition(moveto);
    }
}
