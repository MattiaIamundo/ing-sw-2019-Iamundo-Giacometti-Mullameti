package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.CyberbladeSetEv;
import it.polimi.sw2019.model.weapon_power.Cyberblade;
import it.polimi.sw2019.view.Observer;

public class CyberbladeCont extends SameSquare implements Observer<CyberbladeSetEv> {

    private Cyberblade realmodel;

    public CyberbladeCont(Cyberblade realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        realmodel.chooseTarget(attacker, valid, notreachable);
    }

    @Override
    public void update(CyberbladeSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
