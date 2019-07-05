package it.polimi.sw2019.events;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.view.ControllerClasses.TableController;

import java.io.Serializable;
import java.util.List;

public interface NotifyClient extends Serializable {

    List<Player> getPlayerList();
    Table getGameBoard();
    void visit(ExecutorEventClient executorEventClient, TableController tableController);
}
