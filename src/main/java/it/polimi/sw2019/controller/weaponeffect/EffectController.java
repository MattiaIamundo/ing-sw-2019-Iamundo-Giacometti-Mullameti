package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

/**
 * This class represent the superclass of every weapon effect controller
 */
public abstract class EffectController extends Observable<NotifyReturn> {

    /**
     * This method activate the effect
     * @param attacker is the player that invoke the effect
     * @param players is the list of the players in the math
     * @param gamemap is the map of the match
     */
    public abstract void useEffect(Player attacker, ArrayList<Player> players, Map gamemap);

    public void update(ActionEv message) {
        throw new UnsupportedOperationException();
    }
}
