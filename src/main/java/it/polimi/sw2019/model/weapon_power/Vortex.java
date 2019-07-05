package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

public class Vortex extends SingleTarget implements Power{
    //basic effect for Vortex
    private Space vortex; //save vortex position

    @Override
    public void usePower(Player attacker){
        target.setPosition(vortex);
        super.giveDamage(attacker,2);
    }

    public void setVortex(Space vortex) {
        this.vortex = vortex;
    }

    public Space getVortex() {
        return vortex;
    }

}
