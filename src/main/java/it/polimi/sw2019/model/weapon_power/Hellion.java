package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.events.weaponEffectController_events.HellionChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the basic effect of Hellion
 * @author Mattia Iamundo
 */
public class Hellion extends Observable<HellionChooseEv> implements Power, SingleTarget{

    private Player target;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 1);
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition() == target.getPosition()){
                Table.getPlayers(i).getPlance().setMark(attacker);
            }
        }
    }

    public void chooseTarget(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable){
        notify(new HellionChooseEv(attacker, valid, notreachable));
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
