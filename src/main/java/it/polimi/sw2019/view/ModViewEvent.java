package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.nethandler.ModViewEventInt;

import java.net.Socket;

public class ModViewEvent implements ModViewEventInt {

    Socket socket;

    public ModViewEvent (Socket socket) {
        this.socket = socket;
    }


    AmmoView ammoView;
    //ammo has to be deserialized
    @Override
    public void updateAmmo (Ammo ammo) {
        ammoView.update( ammo );
    }
}
