package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;

/**
 * this class stands for the game's turn
 * @author Luca Giacometti
 */
public abstract class Turn {
    private Player player;
    private int usedAction;
    private Action action;

    /**
     * this is the constructor
     * @param player who is playing
     * @param usedAction how many actions he used during this turn
     * @param action the action on air
     */
    public Turn (Player player, int usedAction, Action action){

        this.player = player;
        this.action = action;
        this.usedAction = usedAction;
    }

    /**
     *
     * @return the player who is playing this turn
     */
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player actualGamer) {
        player = actualGamer;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public int getUsedAction() {
        return this.usedAction;
    }

    public void setUsedAction(int usedAction) {
        this.usedAction = usedAction;
    }

    public void addUsedAction() {
        this.usedAction++;
    }

    public void minusUsedAction() {
        this.usedAction--;
    }

}
