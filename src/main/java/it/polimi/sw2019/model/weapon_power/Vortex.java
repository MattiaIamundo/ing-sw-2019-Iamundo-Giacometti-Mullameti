package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.events.weaponEffectController_events.VortexChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;

public class Vortex extends Observable<VortexChooseEv> implements Power{
    //basic effect for Vortex
    private Space vortex; //save vortex position
    private Player target;

    @Override
    public void usePower(Player attacker){
        target.setPosition(vortex);
        target.getPlance().giveDamage(attacker, 2);
        target.getPlance().removeMark(attacker);
    }

    public void setVortex(Space vortex) {
        this.vortex = vortex;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public void chooseVortexAndTarget(HashMap<String, ArrayList<String>> valid, Player attacker){
        notify(new VortexChooseEv(valid, attacker));
    }

    public Space getVortex() {
        return vortex;
    }

    public Player getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
