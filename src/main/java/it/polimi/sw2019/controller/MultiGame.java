package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.Match;

import java.util.ArrayList;
import java.util.List;

public class MultiGame {
    //it is not a fixed capacity, but it is only to initialize the array list to accelerate for the first ten games
    //avoiding the realloc
    private List<Match> games;

    public MultiGame() {
        games  = new ArrayList<>(10);
    }

    public List<Match> getGames() {
        return games;
    }

}
