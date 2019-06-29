package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.HeatseekerSetEv;
import it.polimi.sw2019.model.weapon_power.Heatseeker;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

public class HeatseekerCont extends VisibleTargetCont implements Observer<HeatseekerSetEv> {

    private Heatseeker realmodel;

    public HeatseekerCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Heatseeker) realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        realmodel.chooseTarget(attacker, notreachable, valid);
    }

    @Override
    public void update(HeatseekerSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
