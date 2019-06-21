package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.ScannerModeSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.ScannerMode;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.view.Observer;

public class ScannerModeCont extends VisibleTargetCont implements Observer<ScannerModeSetEv> {

    private ScannerMode realmodel;

    public ScannerModeCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (ScannerMode) realmodel;
    }

    @Override
    protected void acquireTarget() {
        super.acquireTarget();
        realmodel.chooseTargets(attacker, valid, notreachable);
    }

    @Override
    public void update(ScannerModeSetEv message) {
        realmodel.setTarget(checkPlayer(message.getTarget1()));
        realmodel.setTarget2(checkPlayer(message.getTarget2()));
        realmodel.setTarget3(checkPlayer(message.getTarget3()));
        realmodel.usePower(attacker);
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
