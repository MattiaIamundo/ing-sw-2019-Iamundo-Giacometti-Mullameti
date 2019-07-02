package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.CyberbladeChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.CyberbladeSetEv;
import it.polimi.sw2019.model.weapon_power.Cyberblade;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

public class CyberbladeCont extends SameSquare implements Observer<CyberbladeSetEv> {

    private Cyberblade realmodel;

    public CyberbladeCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Cyberblade) realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        notify(new CyberbladeChooseEv(attacker.getNickname(), valid, notreachable));
    }

    @Override
    public void update(CyberbladeSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
