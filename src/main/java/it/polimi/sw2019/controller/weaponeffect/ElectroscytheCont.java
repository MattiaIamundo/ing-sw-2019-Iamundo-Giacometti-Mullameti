package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.DamageAllSameSpace;

public class ElectroscytheCont implements EffectController{

    private DamageAllSameSpace model;

    public ElectroscytheCont(DamageAllSameSpace model) {
        this.model = model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            model.usePower(attacker);
        }
    }
}
