package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.events.TracBeamChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;

public class TractorBeam extends Observable<TracBeamChooseEv> implements Power{
    //base effect for TractorBeam
    private Player target = null;

    @Override
    public void usePower(Player attacker){
        target.getPlance().giveDamage(attacker, 1);
    }

    public void chooseTarget(HashMap<String, ArrayList<String>> valid, ArrayList<String> notselectable, Player attacker){
        notify(new TracBeamChooseEv(valid, notselectable, attacker));
    }

    public void setTarget(Player target, Space position){
        this.target = target;
        target.setPosition(position);
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
