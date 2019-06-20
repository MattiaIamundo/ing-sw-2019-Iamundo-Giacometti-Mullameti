package it.polimi.sw2019.model;

import it.polimi.sw2019.controller.Game;

public class Match {

    private Game gamecontroller;

    public Match() {
        this.gamecontroller = new Game();
    }

    public Game getGamecontroller() {
        return this.gamecontroller;
    }
}
