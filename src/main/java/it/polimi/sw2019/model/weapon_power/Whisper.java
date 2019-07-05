package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

public class Whisper extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,3);
        super.setMark(attacker);
    }
}
