package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.MoveTargetSetEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.RocketJumpChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.RocketJumpSetEv;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.RocketJump;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class RocketJumpCont extends MovePlayer implements Observer<RocketJumpSetEv> {

    private RocketJump realmodel;

    public RocketJumpCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (RocketJump) realmodel;
    }

    @Override
    public void acquirePosition(Space playerpos, boolean singlespace) {
        super.acquirePosition(playerpos, singlespace);
        notify(new RocketJumpChooseEv(attacker.getNickname(), new ArrayList<>(positions.keySet())));
    }

    @Override
    public void update(RocketJumpSetEv message) {
        super.update((MoveTargetSetEv) message);
        realmodel.usePower(attacker);
    }
}
