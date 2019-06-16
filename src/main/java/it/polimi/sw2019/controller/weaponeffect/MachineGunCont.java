package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.MachineGunSetEv;
import it.polimi.sw2019.model.weapon_power.MachineGun;
import it.polimi.sw2019.view.Observer;

public class MachineGunCont extends VisibleTargetCont implements Observer<MachineGunSetEv> {

    private MachineGun realmodel;

    public MachineGunCont(MachineGun realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        realmodel.chooseTarget(attacker, valid, notreachable);
    }

    @Override
    public void update(MachineGunSetEv message) {
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getNickname().equals(message.getTarget1())){
                realmodel.setTarget(Table.getPlayers(i));
            } else if ((message.getTarget2() != null) && (Table.getPlayers(i).getNickname().equals(message.getTarget2()))) {
                realmodel.setTarget2(Table.getPlayers(i));
            }
        }
        if (message.getTarget2() == null){
            realmodel.setTarget2(null);
        }
        realmodel.usePower(attacker);
    }
}
