package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.PlasmaGunChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.PlasmaGunSetEv;
import it.polimi.sw2019.model.weapon_power.PlasmaGun;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

public class PlasmaGunCont extends VisibleTargetCont implements Observer<PlasmaGunSetEv> {

    private PlasmaGun realmodel;

    public PlasmaGunCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (PlasmaGun) realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        notify(new PlasmaGunChooseEv(attacker.getNickname(), valid, notreachable));
    }

    @Override
    public void update(PlasmaGunSetEv message) {
        //super.update(message);
        realmodel.usePower(attacker);
    }
}
