package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.SledgehammerChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.SledgehammerSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.Sledgehammer;
import it.polimi.sw2019.view.Observer;

public class SledgehammerCont extends SameSquare implements Observer<SledgehammerSetEv> {

    private Sledgehammer realmodel;

    public SledgehammerCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Sledgehammer) realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        notify(new SledgehammerChooseEv(attacker.getNickname(), valid, notreachable));
    }

    @Override
    public void update(SledgehammerSetEv message) {
        //super.update(message);
        realmodel.usePower(attacker);
    }
}
