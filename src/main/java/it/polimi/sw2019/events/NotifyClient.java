package it.polimi.sw2019.events;

import it.polimi.sw2019.view.ControllerClasses.TableController;

public interface NotifyClient {

    void visit(ExecutorEventClient executorEventClient, TableController tableController);
}
