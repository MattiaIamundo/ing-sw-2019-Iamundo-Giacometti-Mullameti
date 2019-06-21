package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

public interface EffectController {

    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap);
}
