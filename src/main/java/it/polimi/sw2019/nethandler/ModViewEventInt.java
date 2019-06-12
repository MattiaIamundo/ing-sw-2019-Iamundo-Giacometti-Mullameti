package it.polimi.sw2019.nethandler;

import it.polimi.sw2019.model.Ammo;

import java.rmi.Remote;

public interface ModViewEventInt extends Remote {

    /**
     * to show the ammo's which is be updated
     * @param ammo the ammo updated
     */
    void updateAmmo (Ammo ammo);
}
