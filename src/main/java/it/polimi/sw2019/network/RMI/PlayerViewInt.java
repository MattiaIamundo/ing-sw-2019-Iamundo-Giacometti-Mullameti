package it.polimi.sw2019.network.RMI;

import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.model.PowerUp;
import it.polimi.sw2019.model.Weapon;

import java.rmi.Remote;

public interface PlayerViewInt extends Remote {
    String getPlayerNickname();

    Integer getPlayerScore();

    Weapon[] getPlayerWeapon();

    Ammo[] getPlayerAmmo();

    PowerUp[] getPlayerPowerUp();
}
