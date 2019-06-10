package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.FragmentingWarhead;

public class FragmentingWarheadCont implements EffectController{

    private FragmentingWarhead model;

    public FragmentingWarheadCont(FragmentingWarhead model) {
        this.model = model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            model.usePower(attacker);
        }
    }
}
