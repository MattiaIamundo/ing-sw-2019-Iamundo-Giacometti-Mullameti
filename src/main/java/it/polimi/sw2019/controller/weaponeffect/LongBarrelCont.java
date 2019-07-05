package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.LongBarrelChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.LongBarrelSetEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.TargetSetEv;
import it.polimi.sw2019.model.weapon_power.LongBarrelMode;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

/**
 * This class represent the controller of Long barrel mode, the alternative effect of Shotgun
 */
public class LongBarrelCont extends OneMoveAway implements Observer<LongBarrelSetEv> {
    private LongBarrelMode realmodel;

    /**
     * @param realmodel the model of the effect
     */
    public LongBarrelCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (LongBarrelMode) realmodel;
    }

    /**
     * This method check if a player can be selected as a target or not
     */
    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        notify(new LongBarrelChooseEv(attacker.getNickname(), valid, notreachable));
    }

    /**
     * This method catch a LongBarrelModeSetEv event
     * @param message the object which have to be updated
     */
    @Override
    public void update(LongBarrelSetEv message) {
        super.update((TargetSetEv) message);
        realmodel.usePower(attacker);
    }
}
