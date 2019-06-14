package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.ThorSetEv;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.model.weapon_power.Thor;
import it.polimi.sw2019.view.Observer;

public class ThorCont extends VisibleTargetCont implements Observer<ThorSetEv> {

    private Thor realmodel;

    public ThorCont(Thor realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        realmodel.chooseTarget(attacker, valid, notreachable);
    }

    @Override
    public void update(ThorSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
