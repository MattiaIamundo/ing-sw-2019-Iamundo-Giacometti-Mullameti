package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.RailGunSetEv;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.RailGun;
import it.polimi.sw2019.model.weapon_power.ThroughWalls;
import it.polimi.sw2019.view.Observer;

public class RailGunCont extends ThoughWall implements Observer<RailGunSetEv>{

    private RailGun realmodel;

    public RailGunCont(RailGun realmodel) {
        super(realmodel);
        this.realmodel = realmodel;
    }

    @Override
    public void update(RailGunSetEv message) {
        int i = 0;

        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(message.getTarget()))){
            i++;
        }
        realmodel.setTarget(Table.getPlayers(i));
        realmodel.usePower(attacker);
    }
}
