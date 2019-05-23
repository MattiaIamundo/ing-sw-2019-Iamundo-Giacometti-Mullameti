package it.polimi.sw2019.view;

import it.polimi.sw2019.model.PowerUp;

public class PowerUpView extends ObservableByGame implements Observer <PowerUp>{

    private PowerUp powerup;

    protected void showPowerUp() {}

    /**
     * this method show the update that one player did
     */
    public void update(PowerUp message) {
        showPowerUp();
    }

}
