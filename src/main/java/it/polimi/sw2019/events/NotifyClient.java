package it.polimi.sw2019.events;

import it.polimi.sw2019.view.ControllerClasses.TableController;

import java.io.Serializable;

public interface NotifyClient extends Serializable {

    void visit(ExecutorEventClient executorEventClient, TableController tableController);
}
