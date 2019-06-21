package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Electroscythe;
import it.polimi.sw2019.model.weapon_power.Power;

import java.util.ArrayList;

public class ElectroscytheCont implements EffectController{

    private Electroscythe model;

    public ElectroscytheCont(Power model) {
        this.model = (Electroscythe) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        model.usePower(attacker);
    }
}
