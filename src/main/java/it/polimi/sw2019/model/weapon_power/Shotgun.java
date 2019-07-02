package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.events.weaponeffect_controller_events.ShotgunChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the basic effect of Shotgun
 * @author Mattia Iamundo
 */
public class Shotgun extends SingleTarget implements Power{
    private Space moveto;

    @Override
    public void usePower(Player attacker){
        super.usePower(attacker,3);
        if (moveto != null){
            target.setPosition(moveto);
        }
    }

    public Space getMoveto() {
        return moveto;
    }

    public void setMoveto(Space moveto) {
        this.moveto = moveto;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
