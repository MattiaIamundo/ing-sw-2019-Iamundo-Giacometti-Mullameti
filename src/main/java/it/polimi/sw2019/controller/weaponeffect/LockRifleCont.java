package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.LockRifleChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.LockRifleSetEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.TargetSetEv;
import it.polimi.sw2019.model.weapon_power.LockRifle;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

/**
 * This class represent the controller of Lock rifle, the basic effect of Lock Rifle
 */
public class LockRifleCont extends VisibleTargetCont implements Observer<LockRifleSetEv> {
    private LockRifle realmodel;

    /**
     * @param realmodel the model of the effect
     */
    public LockRifleCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (LockRifle) realmodel;
    }

    /**
     * This method check if a player can be selected as a target or not
     */
    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        notify(new LockRifleChooseEv(attacker.getNickname(), valid, notreachable));
    }

    /**
     * This method catch a LockRifleSetEv event
     * @param message the object which have to be updated
     */
    @Override
    public void update(LockRifleSetEv message) {
        super.update((TargetSetEv) message);
        realmodel.usePower(attacker);
    }
}
