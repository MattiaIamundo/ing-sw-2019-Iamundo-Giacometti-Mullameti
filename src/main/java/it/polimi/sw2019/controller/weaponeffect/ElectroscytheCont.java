package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Electroscythe;
import it.polimi.sw2019.model.weapon_power.Power;

public class ElectroscytheCont implements EffectController{

    private Electroscythe model;

    public ElectroscytheCont(Power model) {
        this.model = (Electroscythe) model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            model.usePower(attacker);
        }
    }
}
