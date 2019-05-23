package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.nethandler.ViewContEventInt;

import java.net.Socket;

import static it.polimi.sw2019.view.AmmoView.showAmmo;

public class ViewContEvent implements ViewContEventInt {

    Socket socket;

    public ViewContEvent (Socket socket) {
        this.socket = socket;
    }


    //ammo has to be deserialized
    @Override
    public void showTheAmmo(Ammo ammo) {
        showAmmo( ammo );
    }
}
