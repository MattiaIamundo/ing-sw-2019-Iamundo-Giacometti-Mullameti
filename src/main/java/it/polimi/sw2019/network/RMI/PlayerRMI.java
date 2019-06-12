package it.polimi.sw2019.network.RMI;

import it.polimi.sw2019.model.Player;

import java.io.Serializable;

public class PlayerRMI implements Serializable {

    private final int serialVersionUID = 758;
    private Player myplayer;
    private int turnToGame = 0;

    public PlayerRMI () {
        myplayer = new Player("null", 0, null, null);
    }

    public String getNickname() {
        return myplayer.getNickname();
    }

    public void setNickname(String nick) {
        myplayer.setNickname(nick);
    }

    public int getTurnToGame() {
        return turnToGame;
    }

    public void setTurnToGame(int turnGame) {
        this.turnToGame = turnGame;
    }

}
