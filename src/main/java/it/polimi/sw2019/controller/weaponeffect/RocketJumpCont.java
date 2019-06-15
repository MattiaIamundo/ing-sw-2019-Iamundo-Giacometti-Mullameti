package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.RocketJumpSetEv;
import it.polimi.sw2019.model.weapon_power.RocketJump;
import it.polimi.sw2019.view.Observer;

public class RocketJumpCont extends MovePlayer implements Observer<RocketJumpSetEv> {

    private RocketJump realmodel;

    public RocketJumpCont(RocketJump realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    public void update(RocketJumpSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
