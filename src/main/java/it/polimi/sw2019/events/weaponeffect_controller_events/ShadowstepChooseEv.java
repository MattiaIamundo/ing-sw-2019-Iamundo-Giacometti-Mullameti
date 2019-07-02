package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Shadowstep, the first optional effect of Cyberblade
 */
public class ShadowstepChooseEv extends MovePlayerChooseEv{

    /**
     * @param attacker is the player that invoke the effect
     * @param positions is the list of the squares in which the attacker can be move in, they are expressed by their cardinal direction e.g. north, south
     */
    public ShadowstepChooseEv(String attacker, ArrayList<String> positions) {
        super(attacker, positions);
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
