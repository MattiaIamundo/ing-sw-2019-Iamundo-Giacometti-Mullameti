package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.events.ShadowstepChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implement the first optional effect of Cyberblade
 * @author Mattia Iamundo
 */
public class Shadowstep extends Observable<ShadowstepChooseEv> implements Power, WithMove{

    private Space moveto;

    @Override
    public void usePower(Player attacker){
        attacker.setPosition(moveto);
    }

    @Override
    public void changePosition(Player attacker, ArrayList<String> positions) {
        notify(new ShadowstepChooseEv(attacker, positions));
    }

    @Override
    public void setMoveto(Space moveto) {
        this.moveto = moveto;
    }
}
