package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.FlamethrowerSetEv;
import it.polimi.sw2019.model.weapon_power.Flamethrower;
import it.polimi.sw2019.view.Observer;


public class FlamethrowerCont extends LineFireCont implements Observer<FlamethrowerSetEv>{

    private Flamethrower model;

    public FlamethrowerCont(Flamethrower model) {
        this.model = model;
    }

    @Override
    public void update(FlamethrowerSetEv message) {
        super.update(message);
        model.usePower(attacker);
    }
}
