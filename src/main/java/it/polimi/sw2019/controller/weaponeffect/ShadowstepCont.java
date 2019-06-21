package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.events.ShadowstepSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.Shadowstep;
import it.polimi.sw2019.view.Observer;

public class ShadowstepCont extends MovePlayer implements Observer<ShadowstepSetEv> {

    private Shadowstep realmodel;

    public ShadowstepCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Shadowstep) realmodel;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (realmodel.toString().equals(effectname)){
            this.attacker = attacker;
            acquirePosition(attacker.getPosition(), true);
        }
    }

    @Override
    public void update(ShadowstepSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
