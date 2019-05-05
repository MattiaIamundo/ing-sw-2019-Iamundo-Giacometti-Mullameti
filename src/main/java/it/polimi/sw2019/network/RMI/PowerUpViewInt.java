package it.polimi.sw2019.network.RMI;

import java.rmi.Remote;

public interface PowerUpViewInt extends Remote {

    String getPowerUpName();

    String getPowerUpDescription();

    String getPowerUpColor();

    String getPowerUpEffect();
}
