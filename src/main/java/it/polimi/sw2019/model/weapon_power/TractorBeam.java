package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

public class TractorBeam extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,1);
    }

    public void setTarget(Player target, Space position){
        this.target = target;
        target.setPosition(position);
    }

}
