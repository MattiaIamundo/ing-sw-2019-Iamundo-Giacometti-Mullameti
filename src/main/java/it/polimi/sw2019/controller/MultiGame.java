package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.Match;

import java.util.ArrayList;
import java.util.List;

public class MultiGame {
    //it is not a fixed capacity, but it is only to initialize the array list
    //to avoid the realloc for the first ten games
    private List<Match> games;

    public MultiGame() {
        games  = new ArrayList<>(10);
    }

    public List<Match> getGames() {
        return games;
    }

    public void setGames(Match match) {
        this.games.add(match);
    }

    public String createGame() {

        Match match = new Match();
        match.setNameMatch( this.games );
        this.games.add(match);

        return match.getNameMatch();
    }
}
