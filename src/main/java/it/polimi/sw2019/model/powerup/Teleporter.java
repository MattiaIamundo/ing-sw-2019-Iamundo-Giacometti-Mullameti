package it.polimi.sw2019.model.powerup;

import it.polimi.sw2019.events.powerup_events.TeleporterChooseEv;
import it.polimi.sw2019.model.EffectBehaviour;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.view.Observable;

import java.io.Serializable;
import java.util.ArrayList;

/**Class Teleporter: the power up card described in this class gives you the possibility to move from one space to another
 * @author Merita Mullameti
 */
public class Teleporter implements EffectBehaviour, Serializable {
    private Space moveto;

    /**
     * @param target the player  who is going to be attacked using this power up card
     */
    @Override
    public void useEffect(Player target) {
        target.setPosition(moveto);
    }

    public Space getMoveto() {
        return moveto;
    }

    public void setMoveto(Space moveto) {
        this.moveto = moveto;
    }
}
