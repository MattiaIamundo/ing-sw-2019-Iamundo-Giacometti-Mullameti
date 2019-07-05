package it.polimi.sw2019.events.client_event.MVevent;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventClient;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyClient;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.view.ControllerClasses.TableController;

import java.util.ArrayList;
import java.util.List;

public class NotifyMoveEv implements NotifyReturn, NotifyClient {

    private String nickname;
    private String changeName;
    private List<Player> playerList;
    private Table boardGame;

    public NotifyMoveEv(String nickname, String changeName) {
        this.nickname = nickname;
        this.changeName = changeName;
        playerList = new ArrayList<>(5);
        boardGame = new Table();
    }

    public String getChangeName() {
        return this.changeName;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public Table getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(Table boardGame) {
        this.boardGame = boardGame;
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

    public void visit(ExecutorEventClient executorEventClient, TableController tableController) {
        executorEventClient.handleEvent(this,tableController);
    }
}
