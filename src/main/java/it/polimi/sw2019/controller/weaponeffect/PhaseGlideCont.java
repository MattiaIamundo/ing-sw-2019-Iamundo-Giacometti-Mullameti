package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.PhaseGlideChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.PhaseGlideSetEv;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.PhaseGlide;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class PhaseGlideCont extends MovePlayer implements Observer<PhaseGlideSetEv> {

    private PhaseGlide realmodel;

    public PhaseGlideCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (PhaseGlide) realmodel;
    }

    @Override
    public void acquirePosition(Space playerpos, boolean singlespace) {
        super.acquirePosition(playerpos, singlespace);
        notify(new PhaseGlideChooseEv(attacker.getNickname(), new ArrayList<>(positions.keySet())));
    }

    @Override
    public void update(PhaseGlideSetEv message) {
        //super.update(message);
        realmodel.usePower(attacker);
    }
}
