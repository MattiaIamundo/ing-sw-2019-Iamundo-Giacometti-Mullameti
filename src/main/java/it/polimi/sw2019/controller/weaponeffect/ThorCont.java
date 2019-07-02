package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.ThorChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.ThorSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.Thor;
import it.polimi.sw2019.view.Observer;

public class ThorCont extends VisibleTargetCont implements Observer<ThorSetEv> {

    private Thor realmodel;

    public ThorCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Thor) realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        notify(new ThorChooseEv(attacker.getNickname(), valid, notreachable));
    }

    @Override
    public void update(ThorSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
