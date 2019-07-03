package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public abstract class EffectController extends Observable<NotifyReturn> {

    public abstract void useEffect(Player attacker, ArrayList<Player> players, Map gamemap);

    public void update(ActionEv message) {
        throw new UnsupportedOperationException();
    }
}
