package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.PhaseGlideSetEv;
import it.polimi.sw2019.model.weapon_power.PhaseGlide;
import it.polimi.sw2019.view.Observer;

public class PhaseGlideCont extends MovePlayer implements Observer<PhaseGlideSetEv> {

    private PhaseGlide realmodel;

    public PhaseGlideCont(PhaseGlide realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    public void update(PhaseGlideSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
