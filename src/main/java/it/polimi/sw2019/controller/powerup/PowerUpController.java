package it.polimi.sw2019.controller.powerup;

import it.polimi.sw2019.model.Player;

/**
 * The interface that all the PowerUp controllers must implement
 */
public interface PowerUpController {

    /**
     * This method search for the players that can be selected as a valid target for the powerUp
     * @param attacker is the player that use the powerUp
     */
    void usePowerUp(Player attacker);
}
