package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.FlamethrowerChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.FlamethrowerSetEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.LineFireSetEv;
import it.polimi.sw2019.model.weapon_power.Flamethrower;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

/**
 * This class represent the controller of Flamethrower, the basic effect of Flamethrower
 */
public class FlamethrowerCont extends LineFireCont implements Observer<FlamethrowerSetEv>{
    private Flamethrower realmodel;

    /**
     * @param realmodel the model of the basic effect
     */
    public FlamethrowerCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Flamethrower) realmodel;
    }

    /**
     * This method check which are the direction in which the attacker can shoot
     */
    @Override
    protected void acquireTargets() {
        super.acquireTargets();
        notify(new FlamethrowerChooseEv(attacker.getNickname(), firststep, secondstep));
    }

    /**
     * This method catch a FlamethrowerSetEv event
     * @param message the object which have to be updated
     */
    @Override
    public void update(FlamethrowerSetEv message) {
        super.update((LineFireSetEv) message);
        realmodel.usePower(attacker);
    }
}
