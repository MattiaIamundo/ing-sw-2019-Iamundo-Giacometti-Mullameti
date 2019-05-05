package it.polimi.sw2019.network.RMI;

import java.rmi.Remote;

public interface WeaponViewInt extends Remote {
    String getWeaponName();

    String getWeaponDescription();

    String[] getWeaponCost();

    String getWeaponeffect();
}
