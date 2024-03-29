package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.RailGunChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.RailGunSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.RailGun;
import it.polimi.sw2019.view.Observer;

public class RailGunCont extends ThoughWall implements Observer<RailGunSetEv>{

    private RailGun realmodel;

    public RailGunCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (RailGun) realmodel;
    }

    @Override
    public void acquireTarget() {
        super.acquireTarget();
        notify(new RailGunChooseEv(attacker.getNickname(), validlist));
    }

    @Override
    public void update(RailGunSetEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget())){
                realmodel.setTarget(player);
            }
        }
        realmodel.usePower(attacker);
    }
}
