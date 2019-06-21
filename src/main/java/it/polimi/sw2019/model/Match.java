package it.polimi.sw2019.model;

import it.polimi.sw2019.controller.Game;

import java.util.List;

public class Match {

    private Game gamecontroller;
    private String nameMatch;

    public Match() {

        this.gamecontroller = new Game();
    }

    public Game getGamecontroller() {
        return this.gamecontroller;
    }

    public String setNameMatch(List<Match> matchList) {
        //a random Integer to string to set as nameMatch
        Integer numberToString = 1;
        //create a method to control the ID code for this match
        return  numberToString.toString();
    }

    public String getNameMatch() {
        return this.nameMatch;
    }
}
