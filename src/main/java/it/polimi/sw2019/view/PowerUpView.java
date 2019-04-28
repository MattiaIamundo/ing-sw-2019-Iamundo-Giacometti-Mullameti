package it.polimi.sw2019.view;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.model.PowerUp;

public abstract class PowerUpView extends Observable <Game> implements Observer <PowerUp>{

    //private PowerUp powerup;

   protected abstract void showPowerUp();

    /**
     * this method show the update that one player did
     */
    public void update(PowerUp message) {
        showPowerUp();
    }

}
