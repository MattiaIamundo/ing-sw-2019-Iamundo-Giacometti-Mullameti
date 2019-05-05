package it.polimi.sw2019.network.RMI;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;

import java.rmi.Remote;

public interface TableViewInt extends Remote {
    Player[] getListOfPlayers();

    Player getTurnOf();

    Integer getRemainingSkull();

    Map getMap();
}
