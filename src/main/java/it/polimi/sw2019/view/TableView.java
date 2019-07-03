package it.polimi.sw2019.view;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.events.client_event.Cevent.StartGameEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * this is the class view for the client
 * @author Luca Giacometti
 */
public class TableView {


    private ExecutorEventImp executorEventImp = new ExecutorEventImp();
    private UIinterface ui;

    public TableView() {}
    public TableView(UIinterface userImp) {
        ui = userImp;
    }


}
