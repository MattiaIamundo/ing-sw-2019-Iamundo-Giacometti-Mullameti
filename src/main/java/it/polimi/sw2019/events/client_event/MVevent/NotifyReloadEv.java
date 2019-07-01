package it.polimi.sw2019.events.client_event.MVevent;

import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.List;

public class NotifyReloadEv extends NotifyReturn {

    private String changeName;
    private List<Player> playerList;
    private Table boardGame;

    public NotifyReloadEv(String nickname, String changeName) {
        super(nickname);
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


}
