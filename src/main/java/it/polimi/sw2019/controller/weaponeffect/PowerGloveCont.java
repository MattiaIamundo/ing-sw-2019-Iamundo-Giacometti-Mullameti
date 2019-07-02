package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.PowerGloveChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.PowerGloveSetEv;
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
        notify(new PowerGloveChooseEv(attacker.getNickname(), valid, notreachable));
    }

    @Override
    public void update(PowerGloveSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
