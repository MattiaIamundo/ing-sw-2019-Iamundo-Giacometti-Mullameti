package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Events.VortexChooseEv;
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
    }

    public void setVortex(Space vortex) {
        this.vortex = vortex;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public void chooseVortexAndTarget(HashMap<String, ArrayList<String>> valid){
        notify(new VortexChooseEv(valid));
    }

    public Space getVortex() {
        return vortex;
    }

    public Player getTarget() {
        return target;
    }
}
