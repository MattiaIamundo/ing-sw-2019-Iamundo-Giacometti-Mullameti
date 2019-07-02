package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.events.weaponeffect_controller_events.TractorBeamChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;

public class TractorBeam extends Observable<TractorBeamChooseEv> implements Power{
    //base effect for TractorBeam
    private Player target = null;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 1);
        target.getPlance().removeMark(attacker);
    }

    public void chooseTarget(HashMap<String, ArrayList<String>> valid, Player attacker){
        notify(new TractorBeamChooseEv(valid, attacker));
    }

    public void setTarget(Player target, Space position){
        this.target = target;
        target.setPosition(position);
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
