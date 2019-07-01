package it.polimi.sw2019.events.client_event.MVevent;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.List;

public class NotifyGrabEv implements NotifyReturn {

    private String nickname;
    private String nameChange;
    private List<Player> playerList;
    private Table boardGame;

    public NotifyGrabEv(String nickname, String nameChange) {
        this.nickname = nickname;
        this.nameChange = nameChange;
        boardGame = new Table();
        playerList = new ArrayList<>(5);

    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getNameChange() {
        return this.nameChange;
    }

    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
