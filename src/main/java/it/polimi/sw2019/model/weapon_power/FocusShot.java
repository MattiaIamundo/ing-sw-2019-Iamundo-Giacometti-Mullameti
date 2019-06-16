package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.events.FocusShotChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the first optional effect of Machine Gun
 * @author Mattia Iamundo
 */
public class FocusShot extends Observable<FocusShotChooseEv> implements Power{
    private Player target;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 1);
    }

    public void chooseTarget(Player attacker, ArrayList<String> targets){
        notify(new FocusShotChooseEv(attacker, targets));
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }
}
