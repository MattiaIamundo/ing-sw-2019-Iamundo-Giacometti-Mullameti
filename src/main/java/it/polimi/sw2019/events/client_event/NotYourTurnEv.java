package it.polimi.sw2019.events.client_event;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventClient;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyClient;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.view.ControllerClasses.TableController;

import java.io.Serializable;

public class NotYourTurnEv implements NotifyClient, NotifyReturn, Serializable {

    private String nickname;

    @Override
    public void visit(ExecutorEventClient executorEventClient, TableController tableController) {
        executorEventClient.handleEvent(this, tableController);
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
