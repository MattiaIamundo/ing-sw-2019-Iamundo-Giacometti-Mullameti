package it.polimi.sw2019.events;

import it.polimi.sw2019.events.client_event.Cevent.DirectionChooseEv;
import it.polimi.sw2019.events.client_event.Cevent.NotifyEndMoveEv;
import it.polimi.sw2019.events.client_event.MVevent.NotifyMoveEv;
import it.polimi.sw2019.events.client_event.StartTurnEv;
import it.polimi.sw2019.view.ControllerClasses.TableController;

public class ExecutorEventClient {

    public void handleEvent(DirectionChooseEv directionChooseEv, TableController tableController) {
        tableController.handleEvent(directionChooseEv);
    }

    public void handleEvent(NotifyMoveEv notifyMoveEv, TableController tableController) {
        tableController.handleEvent(notifyMoveEv);
    }

    public void handleEvent(StartTurnEv startTurnEv, TableController tableController) {
        tableController.handleEvent(startTurnEv);
    }

    public void handleEvent(NotifyEndMoveEv notifyEndMoveEv, TableController tableController) {
        tableController.handleEvent(notifyEndMoveEv);
    }
}
