package it.polimi.sw2019.view;

import java.net.Socket;

public class WeaponRemoteView extends WeaponView {

    private Socket socket;

    public WeaponRemoteView (Socket socket) {
        this.socket = socket;
    }

    protected void showWeapon() {


    }
}
