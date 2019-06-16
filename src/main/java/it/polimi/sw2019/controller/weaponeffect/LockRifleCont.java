package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.LockRifleSetEv;
import it.polimi.sw2019.model.weapon_power.LockRifle;
import it.polimi.sw2019.view.Observer;

public class LockRifleCont extends VisibleTargetCont implements Observer<LockRifleSetEv> {

    private LockRifle realmodel;

    public LockRifleCont(LockRifle realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        realmodel.chooseTarget(attacker, valid, notreachable);
    }

    @Override
    public void update(LockRifleSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
