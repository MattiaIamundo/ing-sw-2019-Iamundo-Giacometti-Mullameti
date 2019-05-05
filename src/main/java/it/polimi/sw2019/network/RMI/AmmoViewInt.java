package it.polimi.sw2019.network.RMI;

import it.polimi.sw2019.model.Ammo;

import java.rmi.Remote;

public interface AmmoViewInt extends Remote {
    Ammo getAmmo();
}
