package it.polimi.sw2019.controller;

import it.polimi.sw2019.exception.IllegalDirectionException;
import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.exception.InvalidNrOfMoves;
import it.polimi.sw2019.model.*;


import java.util.Scanner;

/**
 * this class stands for the game's turn
 * @author Luca Giacometti
 */
public abstract class Turn {
    private static Player player;
    private Integer usedAction;
    private Action action;

    /**
     * this is the constructor
     * @param player who is playing
     * @param usedAction how many actions he used during this turn
     * @param action the action on air
     */
    public Turn (Player player, Integer usedAction, Action action){

        this.player = player;
        this.action = action;
        this.usedAction = usedAction;
    }

    /**
     *
     * @return the player who is playing this turn
     */
    public static Player getPlayer() {
        return player;
    }


}
