package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.PiercingModeChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.PiercingModeSetEv;
import it.polimi.sw2019.model.weapon_power.PiercingMode;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

public class PiercingModeCont extends ThoughWall implements Observer<PiercingModeSetEv> {

    private PiercingMode realmodel;

    public PiercingModeCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (PiercingMode) realmodel;
    }

    @Override
    public void acquireTarget() {
        super.acquireTarget();
        notify(new PiercingModeChooseEv(attacker.getNickname(), validlist));
    }

    @Override
    public void update(PiercingModeSetEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget1())){
                realmodel.setTarget1(player);
            }else if (player.getNickname().equals(message.getTarget2())){
                realmodel.setTarget2(player);
            }
        }
        if (message.getTarget2() == null) {realmodel.setTarget2(null);}
        realmodel.usePower(attacker);
    }
}
