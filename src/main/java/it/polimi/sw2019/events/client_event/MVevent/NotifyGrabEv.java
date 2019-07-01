package it.polimi.sw2019.events.client_event.MVevent;

import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.List;

public class NotifyGrabEv extends NotifyReturn {

    private String nameChange;
    private List<Player> playerList;
    private Table boardGame;

    public NotifyGrabEv(String nickname, String nameChange) {
        super(nickname);
        this.nameChange = nameChange;
        boardGame = new Table();
        playerList = new ArrayList<>(5);

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
}
