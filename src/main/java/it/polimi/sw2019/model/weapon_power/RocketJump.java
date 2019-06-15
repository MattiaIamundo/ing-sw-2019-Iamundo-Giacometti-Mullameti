package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.events.RocketJumpChooseEv;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;

/**
 * This class implements the first optional effect of Rocket Launcher
 * @author Mattia Iamundo
 */
public class RocketJump extends Observable<RocketJumpChooseEv> implements Power, WithMove{

    private Space moveto;

    @Override
    public void usePower(Player attacker){
        attacker.setPosition(moveto);
    }

    @Override
    public void changePosition(Player attacker, ArrayList<String> positions) {
        notify(new RocketJumpChooseEv(attacker, positions));
    }

    @Override
    public void setMoveto(Space moveto) {
        this.moveto = moveto;
    }
}
