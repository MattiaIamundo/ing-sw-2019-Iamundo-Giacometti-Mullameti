package it.polimi.sw2019.model.powerup;


import it.polimi.sw2019.model.EffectBehaviour;
import it.polimi.sw2019.model.Player;

import java.io.Serializable;

/**Class TagbackGrenade: the power up card described in this class gives you the possibility to give 1 mark to one of the players you see
 * @author Merita Mullameti
 */

public class TagbackGrenade implements EffectBehaviour, Serializable {
    private Player target;

    @Override
    public void useEffect(Player attacker) {
        target.getPlance().setMark(attacker);
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }
}