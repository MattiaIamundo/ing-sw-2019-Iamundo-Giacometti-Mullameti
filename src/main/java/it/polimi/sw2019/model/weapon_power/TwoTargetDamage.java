package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

public class TwoTargetDamage implements Power{
    //basic effect for MachineGun
    Player target1; //save the first target
    Player target2; //save the second target [optional]

    @Override
    public void usePower(Player attacker){

    }
}
