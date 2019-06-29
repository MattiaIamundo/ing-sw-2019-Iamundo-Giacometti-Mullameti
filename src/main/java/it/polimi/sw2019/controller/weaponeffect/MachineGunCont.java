package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.MachineGunSetEv;
import it.polimi.sw2019.model.weapon_power.MachineGun;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

public class MachineGunCont extends VisibleTargetCont implements Observer<MachineGunSetEv> {

    private MachineGun realmodel;

    public MachineGunCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (MachineGun) realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        realmodel.chooseTarget(attacker, valid, notreachable);
    }

    @Override
    public void update(MachineGunSetEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget1())){
                realmodel.setTarget(player);
            }else if (player.getNickname().equals(message.getTarget2())){
                realmodel.setTarget2(player);
            }
        }
        if (message.getTarget2() == null){
            realmodel.setTarget2(null);
        }
        realmodel.usePower(attacker);
    }
}
