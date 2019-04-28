package it.polimi.sw2019.view;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.model.Player;

public abstract class PlayerView extends Observable <Game> implements Observer <Player> {

    //private Player player;

    protected abstract void showPlayer();

    /**
     * this method show the update that one player did
     */
    public void update(Player message) {
        showPlayer();
    }
}
