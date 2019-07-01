package it.polimi.sw2019.events.client_event.Cevent;


import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;

import java.util.ArrayList;
import java.util.List;

public class StartGameEv {

    private List<Player> players = new ArrayList<>(5);
    private Table gameboard;

    public StartGameEv(List<Player> players, Table gameboard) {
        this.gameboard = gameboard;
        this.players = players;
    }

    public Table getGameboard() {
        return this.gameboard;
    }

    public List<Player> getPlayers() {
        return this.players;
    }
}
