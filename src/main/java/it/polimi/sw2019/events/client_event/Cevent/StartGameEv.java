package it.polimi.sw2019.events.client_event.Cevent;


import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventClient;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyClient;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.view.ControllerClasses.TableController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StartGameEv implements Serializable, NotifyReturn, NotifyClient {

    private String nickname;
    private List<Player> players = new ArrayList<>(5);
    private Table gameboard;

    public StartGameEv(List<Player> players, Table gameboard) {
        this.gameboard = gameboard;
        this.players = players;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game tableController) {

    }



    @Override
    public void visit(ExecutorEventClient executorEventClient, TableController tableController) {
        tableController.handleEvent(this);
    }

    public Table getGameboard() {
        return this.gameboard;
    }

    public List<Player> getPlayerList() {
        return this.players;
    }

    @Override
    public Table getGameBoard() {
        return this.gameboard;
    }
}
