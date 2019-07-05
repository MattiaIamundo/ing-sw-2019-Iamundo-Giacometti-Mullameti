package it.polimi.sw2019.model.powerup;


import it.polimi.sw2019.events.powerup_events.TargetingScopeChooseEv;
import it.polimi.sw2019.model.EffectBehaviour;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;

import java.io.Serializable;
import java.util.ArrayList;

/**Class TargetingScope: the power up card described in this class gives you the possibility to extra damage one of your enemies
 * @author Merita Mullameti
 */
public class TargetingScope implements EffectBehaviour, Serializable {
    private Player target;

    /**
     * @param attacker the player  who is going to be attacked using this power up card
     */
    @Override
    public void useEffect(Player attacker) {
        target.getPlance().giveDamage(attacker,1);
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }
}
