package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

public class MoveDamage implements Power{
    //base effect for TractorBeam
    private Space targetspace; //save the position where the target is moved
    private Player target;

    @Override
    public void usePower(Player attacker){

    }
}
