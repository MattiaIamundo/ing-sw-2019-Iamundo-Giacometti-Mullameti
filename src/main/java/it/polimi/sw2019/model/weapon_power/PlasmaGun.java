package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.events.PlasmaGunChooseEv;
import it.polimi.sw2019.model.events.ThorChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

public class PlasmaGun extends Observable<PlasmaGunChooseEv> implements Power, SingleTarget{

    private Player target;

    @Override
    public void usePower(Player attacker) {
        target.getPlance().giveDamage(attacker, 2);
    }

    public void chooseTarget(Player attacker, ArrayList<String> valid, ArrayList<String> notreachable){
        notify(new PlasmaGunChooseEv(attacker, valid, notreachable));
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }
}
