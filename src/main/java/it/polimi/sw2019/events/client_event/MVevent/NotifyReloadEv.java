package it.polimi.sw2019.events.client_event.MVevent;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.List;

public class NotifyReloadEv implements NotifyReturn {

    private String nickname;
    private String changeName;
    private List<Player> playerList;
    private Table boardGame;

    public NotifyReloadEv(String nickname, String changeName) {
        this.nickname = nickname;
        this.changeName = changeName;
        playerList = new ArrayList<>(5);
        boardGame = new Table();
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public String getChangeName() {
        return this.changeName;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public void setBoardGame(Table boardGame) {
        this.boardGame = boardGame;
    }

    public Table getBoardGame() {
        return boardGame;
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
}
