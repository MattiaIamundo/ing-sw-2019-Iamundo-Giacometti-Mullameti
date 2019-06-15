package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.PowerGloveSetEv;
import it.polimi.sw2019.model.weapon_power.PowerGlove;
import it.polimi.sw2019.view.Observer;

public class PowerGloveCont extends OneMoveAway implements Observer<PowerGloveSetEv> {

    private PowerGlove realmodel;

    public PowerGloveCont(PowerGlove realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        realmodel.chooseTarget(attacker, valid, notreachable);
    }

    @Override
    public void update(PowerGloveSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
