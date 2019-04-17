package it.polimi.sw2019.model.weapon_power;


import it.polimi.sw2019.model.Player;

/**
 * this is an interface which represents the weapon's powers
 */
public interface Power {

    /**
     *
     * @param attacker the player who throws the attack
     * @param enemy the opponent who will be damaged
     */
    void usePower (Player attacker, Player enemy);

    /**
     *
     * @param attacker the player who throws the attack
     */
    void usePower (Player attacker);

}
