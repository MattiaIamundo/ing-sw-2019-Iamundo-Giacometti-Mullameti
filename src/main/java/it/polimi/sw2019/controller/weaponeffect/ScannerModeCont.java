package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.ScannerModeSetEv;
import it.polimi.sw2019.model.weapon_power.ScannerMode;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.view.Observer;

public class ScannerModeCont extends SingleTargetCont implements Observer<ScannerModeSetEv> {

    private ScannerMode model1;

    public ScannerModeCont(SingleTarget model, ScannerMode model1) {
        super(model);
        this.model1 = model1;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        model1.chooseTargets(attacker, valid, notreachable);
    }

    @Override
    public void update(ScannerModeSetEv message) {
        model1.setTarget(checkPlayer(message.getTarget1()));
        model1.setTarget2(checkPlayer(message.getTarget2()));
        model1.setTarget3(checkPlayer(message.getTarget3()));
        model1.usePower(attacker);
    }

    private Player checkPlayer(String name){
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getNickname().equals(name)){
                return Table.getPlayers(i);
            }
        }
        return null;
    }
}
