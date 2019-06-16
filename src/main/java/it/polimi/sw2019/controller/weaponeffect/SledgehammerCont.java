package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.SledgehammerSetEv;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.model.weapon_power.Sledgehammer;
import it.polimi.sw2019.view.Observer;

public class SledgehammerCont extends SameSquare implements Observer<SledgehammerSetEv> {

    private Sledgehammer realmodel;

    public SledgehammerCont(Sledgehammer realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        realmodel.chooseTarget(attacker, valid, notreachable);
    }

    @Override
    public void update(SledgehammerSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
