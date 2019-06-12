package it.polimi.sw2019.view;

import it.polimi.sw2019.model.PowerUp;

public class PowerUpView extends ObservableByGame implements Observer <PowerUp>{

    private PowerUp powerup;
    private UIinterface ui;

    public PowerUpView() {}
    public PowerUpView(UIinterface userImp) {
        ui = userImp;
    }

    protected void showPowerUp() {}

    /**
     * this method show the update that one player did
     */
    public void update(PowerUp message) {
        showPowerUp();
    }

}
