package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.WithMove;

public class FragWarheadCont extends MovePlayer{

    public FragWarheadCont(WithMove model) {
        super(model);
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquirePosition(attacker.getPosition(), false);
        }
    }
}
