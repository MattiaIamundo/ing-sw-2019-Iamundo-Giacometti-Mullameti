package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class TurretTripodChooseEv extends VisibleChooseEv{

    public TurretTripodChooseEv(String attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable) {
        super(attacker, valid, notselectable, notreachable);
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
