package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponEffectController_events.LongBarrelSetEv;
import it.polimi.sw2019.model.weapon_power.LongBarrelMode;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

public class LongBarrelCont extends OneMoveAway implements Observer<LongBarrelSetEv> {

    private LongBarrelMode realmodel;

    public LongBarrelCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (LongBarrelMode) realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        realmodel.chooseTarget(attacker, valid, notreachable);
    }

    @Override
    public void update(LongBarrelSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
