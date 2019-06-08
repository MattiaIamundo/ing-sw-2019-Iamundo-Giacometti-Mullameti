package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;

public interface EffectController {
    /**
     * This method is needed to use the correct controller and to initialize the attacker
     * @param effectname defines the effect of the weapon to be used
     * @param attacker defines the player that use this effect
     */
    public void useEffect(String effectname, Player attacker);
}
