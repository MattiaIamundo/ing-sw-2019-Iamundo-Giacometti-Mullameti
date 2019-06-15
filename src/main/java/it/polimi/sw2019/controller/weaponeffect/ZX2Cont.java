package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.ZX2SetEv;
import it.polimi.sw2019.model.weapon_power.ZX2;
import it.polimi.sw2019.view.Observer;

public class ZX2Cont extends VisibleTargetCont implements Observer<ZX2SetEv> {

    private ZX2 realmodel;

    public ZX2Cont(ZX2 realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        realmodel.chooseTarget(attacker, valid, notreachable);
    }

    @Override
    public void update(ZX2SetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
