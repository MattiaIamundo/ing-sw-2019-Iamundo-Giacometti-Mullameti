package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.PlasmaGunSetEv;
import it.polimi.sw2019.model.weapon_power.PlasmaGun;
import it.polimi.sw2019.view.Observer;

public class PlasmaGunCont extends VisibleTargetCont implements Observer<PlasmaGunSetEv> {

    private PlasmaGun realmodel;

    public PlasmaGunCont(PlasmaGun realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        realmodel.chooseTarget(attacker, valid, notreachable);
    }

    @Override
    public void update(PlasmaGunSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
