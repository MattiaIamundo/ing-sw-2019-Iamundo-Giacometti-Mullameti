package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.ReaperMode;

public class ReaperModeCont implements EffectController {

    private ReaperMode model;

    public ReaperModeCont(ReaperMode model) {
        this.model = model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            model.usePower(attacker);
        }
    }
}
