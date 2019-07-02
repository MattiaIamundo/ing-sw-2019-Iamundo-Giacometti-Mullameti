package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Heatseeker, the basic effect of Heatseeker
 */
public class HeatseekerChooseEv extends VisibleChooseEv{

    /**
     * @param attacker is the player that invoke the effect
     * @param valid is the list of the player that can be selected as a valid target for the effect
     * @param notreachable is the list of the player that can't be selected as a target due to be visible to the attacker
     */
    public HeatseekerChooseEv(String attacker, ArrayList<String> valid, ArrayList<String> notreachable) {
        super(attacker, valid, notreachable);
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
