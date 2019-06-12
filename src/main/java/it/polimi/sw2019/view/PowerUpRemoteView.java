package it.polimi.sw2019.view;

import java.net.Socket;

public class PowerUpRemoteView extends PowerUpView {

    private Socket socket;

    public PowerUpRemoteView (Socket socket) {
        this.socket = socket;
    }

    protected void showPowerUp(){

    }

}
