package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.LockRifleSetEv;
import it.polimi.sw2019.model.weapon_power.LockRifle;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

public class LockRifleCont extends VisibleTargetCont implements Observer<LockRifleSetEv> {

    private LockRifle realmodel;

    public LockRifleCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (LockRifle) realmodel;
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
