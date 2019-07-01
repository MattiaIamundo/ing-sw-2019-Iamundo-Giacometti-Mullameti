package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponeffect_controller_events.ShadowstepChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implement the first optional effect of Cyberblade
 * @author Mattia Iamundo
 */
public class Shadowstep implements Power, WithMove{

    private Space moveto;

    @Override
    public void usePower(Player attacker){
        attacker.setPosition(moveto);
    }

    @Override
    public void setMoveto(Space moveto) {
        this.moveto = moveto;
    }

    public Space getMoveto() {
        return moveto;
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getName().lastIndexOf('.') + 1);
    }
}
