package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.RailGunSetEv;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.RailGun;
import it.polimi.sw2019.model.weapon_power.ThroughWalls;
import it.polimi.sw2019.view.Observer;

public class RailGunCont extends ThoughWall implements Observer<RailGunSetEv>{

    public RailGunCont(ThroughWalls model) {
        super(model);
    }

    @Override
    public void update(RailGunSetEv message) {
        int i = 0;

        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(message.getTarget()))){
            i++;
        }
        ((RailGun) model).setTarget(Table.getPlayers(i));
        ((RailGun) model).usePower(attacker);
    }
}
