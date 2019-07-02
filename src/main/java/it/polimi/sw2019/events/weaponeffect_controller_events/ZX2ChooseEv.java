package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public class ZX2ChooseEv extends VisibleChooseEv{

    public ZX2ChooseEv(String attacker, ArrayList<String> valid, ArrayList<String> notreachable) {
        super(attacker, valid, notreachable);
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
