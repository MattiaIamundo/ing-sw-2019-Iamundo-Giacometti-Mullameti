package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Chain reaction, the first optional effect of T.H.O.R.
 */
public class ChainReactChooseEv extends VisibleChooseEv{

    /**
     * @param attacker is the player that invoke the effect
     * @param valid is the list of the players that can be selected as a valid target for the effect
     * @param notselectable is the list of the players that can't be selected as a target, the first one is the attacker, the other is the player selected as
     *                      target of the basic effect of the weapon
     * @param notreachable is the list of the players that can't be selected as a target due to be out of the range of the effect
     */
    public ChainReactChooseEv(String attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable) {
        super(attacker, valid, notselectable, notreachable);
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
