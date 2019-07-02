package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.CyberbladeChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

public class Cyberblade extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker) {
        super.giveDamage(attacker,2);
    }
}
