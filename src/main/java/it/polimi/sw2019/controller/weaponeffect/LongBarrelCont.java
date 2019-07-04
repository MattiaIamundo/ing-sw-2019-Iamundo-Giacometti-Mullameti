package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.LongBarrelChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.LongBarrelSetEv;
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
        notify(new LongBarrelChooseEv(attacker.getNickname(), valid, notreachable));
    }

    @Override
    public void update(LongBarrelSetEv message) {
        //super.update(message);
        realmodel.usePower(attacker);
    }
}
