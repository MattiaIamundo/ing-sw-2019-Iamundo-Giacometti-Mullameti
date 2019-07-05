package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.CyberbladeChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.CyberbladeSetEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.TargetSetEv;
import it.polimi.sw2019.model.weapon_power.Cyberblade;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

/**
 * This class represent the controller of Cyberblade, the basic effect of Cyberblade
 */
public class CyberbladeCont extends SameSquare implements Observer<CyberbladeSetEv> {
    private Cyberblade realmodel;

    /**
     * @param realmodel the model of the effect
     */
    public CyberbladeCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Cyberblade) realmodel;
    }

    /**
     * This method identify if a player can be chosen as a target or not
     */
    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        notify(new CyberbladeChooseEv(attacker.getNickname(), valid, notreachable));
    }

    /**
     * This method catch a CyberbladeSetEv event
     * @param message the object which have to be updated
     */
    @Override
    public void update(CyberbladeSetEv message) {
        super.update((TargetSetEv) message);
        realmodel.usePower(attacker);
    }
}
