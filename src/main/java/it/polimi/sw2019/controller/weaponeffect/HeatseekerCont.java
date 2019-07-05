package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.HeatseekerChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.HeatseekerSetEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.TargetSetEv;
import it.polimi.sw2019.model.weapon_power.Heatseeker;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

/**
 * This class represent the controller of Heatseeker, the basic effect of Heatseeker
 */
public class HeatseekerCont extends VisibleTargetCont implements Observer<HeatseekerSetEv> {
    private Heatseeker realmodel;

    /**
     * @param realmodel the model of the effect
     */
    public HeatseekerCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Heatseeker) realmodel;
    }

    /**
     * This method check if a player can be selected as a target or not
     */
    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        notify(new HeatseekerChooseEv(attacker.getNickname(), notreachable, valid));
    }

    /**
     * This method catch a HeatseekerSetEv event
     * @param message the object which have to be updated
     */
    @Override
    public void update(HeatseekerSetEv message) {
        super.update((TargetSetEv) message);
        realmodel.usePower(attacker);
    }
}
