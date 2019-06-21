package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.FlamethrowerSetEv;
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
    public void update(FlamethrowerSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
