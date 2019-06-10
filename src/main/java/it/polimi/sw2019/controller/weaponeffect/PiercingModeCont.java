package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.PiercingModeSetEv;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.PiercingMode;
import it.polimi.sw2019.model.weapon_power.ThroughWalls;
import it.polimi.sw2019.view.Observer;

public class PiercingModeCont extends ThoughWall implements Observer<PiercingModeSetEv> {

    public PiercingModeCont(ThroughWalls model) {
        super(model);
    }

    @Override
    public void update(PiercingModeSetEv message) {
        PiercingMode realmodel = (PiercingMode) model;
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getNickname().equals(message.getTarget1())){
                realmodel.setTarget1(Table.getPlayers(i));
            }
        }
        if (message.getTarget2() != null){
            for (int i = 0; i < 5; i++) {
                if (Table.getPlayers(i).getNickname().equals(message.getTarget2())){
                    realmodel.setTarget2(Table.getPlayers(i));
                }
            }
        }else {
            realmodel.setTarget2(null);
        }
        realmodel.usePower(attacker);
    }
}
