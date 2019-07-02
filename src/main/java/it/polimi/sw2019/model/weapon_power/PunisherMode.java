package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.events.weaponeffect_controller_events.PunisherModeChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the alternative effect of Tractor Beam
 * @author Mattia Iamundo
 */
public class PunisherMode extends SingleTarget implements Power{

    @Override
    public void usePower(Player attacker){
        super.giveDamage(attacker,3);
        target.setPosition(attacker.getPosition());
    }

}
