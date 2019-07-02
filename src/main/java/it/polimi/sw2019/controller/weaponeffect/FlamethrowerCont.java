package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.FlamethrowerChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.FlamethrowerSetEv;
import it.polimi.sw2019.model.weapon_power.Flamethrower;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;


public class FlamethrowerCont extends LineFireCont implements Observer<FlamethrowerSetEv>{

    private Flamethrower realmodel;

    public FlamethrowerCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Flamethrower) realmodel;
    }

    @Override
    protected void acquireTargets() {
        super.acquireTargets();
        notify(new FlamethrowerChooseEv(attacker.getNickname(), firststep, secondstep));
    }

    @Override
    public void update(FlamethrowerSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
