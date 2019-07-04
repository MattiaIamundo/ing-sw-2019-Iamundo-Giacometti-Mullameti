package it.polimi.sw2019.model.powerup;


import it.polimi.sw2019.events.powerup_events.TagbackGrenadeChooseEv;
import it.polimi.sw2019.model.EffectBehaviour;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.view.Observable;

import java.io.Serializable;

/**Class TagbackGrenade: the power up card described in this class gives you the possibility to give 1 mark to one of the players you see
 * @author Merita Mullameti
 */

public class TagbackGrenade extends Observable<TagbackGrenadeChooseEv> implements EffectBehaviour, Serializable {
    private Player target;

    @Override
    public void useEffect(Player attacker) {
        target.getPlance().setMark(attacker);
    }

    public void chooseToMark(String attacker, String target){
        notify(new TagbackGrenadeChooseEv(attacker, target));
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }
}