package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.RocketJumpSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.RocketJump;
import it.polimi.sw2019.view.Observer;

public class RocketJumpCont extends MovePlayer implements Observer<RocketJumpSetEv> {

    private RocketJump realmodel;

    public RocketJumpCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (RocketJump) realmodel;
    }

    @Override
    public void update(RocketJumpSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
