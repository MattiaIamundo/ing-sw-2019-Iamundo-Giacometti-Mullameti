package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponEffectController_events.PowerGloveSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.PowerGlove;
import it.polimi.sw2019.view.Observer;

public class PowerGloveCont extends OneMoveAway implements Observer<PowerGloveSetEv> {

    private PowerGlove realmodel;

    public PowerGloveCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (PowerGlove) realmodel;
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
