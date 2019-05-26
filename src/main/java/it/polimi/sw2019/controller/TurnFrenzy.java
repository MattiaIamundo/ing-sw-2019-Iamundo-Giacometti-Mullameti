package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.Player;

public class TurnFrenzy extends Turn {
    /**
     * this is the constructor
     *
     * @param player     who is playing
     * @param usedAction how many actions he used during this turn
     * @param action     the action on air
     */
    public TurnFrenzy(Player player, Integer usedAction, Action action) {
        super(player, usedAction, action);
    }
}
