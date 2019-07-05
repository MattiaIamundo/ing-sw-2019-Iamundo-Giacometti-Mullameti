package it.polimi.sw2019.events.client_event;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventClient;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyClient;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.view.ControllerClasses.TableController;

import java.io.Serializable;
import java.util.List;

public class StartTurnEv implements NotifyClient, NotifyReturn, Serializable {

    private String nickname;
    private List<Player> players;
    private Table gameBoard;


    public StartTurnEv(List<Player> players, Table gameBoard) {
        this.players = players;
        this.gameBoard = gameBoard;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }

    @Override
    public List<Player> getPlayerList() {
        return this.players;
    }

    @Override
    public Table getGameBoard() {
        return this.gameBoard;
    }

    @Override
    public void visit(ExecutorEventClient executorEventClient, TableController tableController) {
        executorEventClient.handleEvent(this, tableController);
    }
}
