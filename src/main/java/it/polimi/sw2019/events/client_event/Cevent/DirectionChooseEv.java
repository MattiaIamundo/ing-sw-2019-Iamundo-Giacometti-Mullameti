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

public class DirectionChooseEv implements NotifyReturn, NotifyClient, Serializable {

    private String nickname;
    private List<String> movesPlayerCanDo = new ArrayList<>(3);
    private List<Player> players;
    private Table gameboard;


    public DirectionChooseEv(String nickname, List<String> movesPlayerCanDo) {
        this.nickname = nickname;
        this.movesPlayerCanDo = movesPlayerCanDo;
    }

    public List<String> getMovesPlayerCanDo() {
        return this.movesPlayerCanDo;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }

    @Override
    public List<Player> getPlayerList() {
        return this.players;
    }

    @Override
    public Table getGameBoard() {
        return this.gameboard;
    }

    public void visit(ExecutorEventClient executorEventClient, TableController tableController) {
        executorEventClient.handleEvent(this, tableController);
    }


}
