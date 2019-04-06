package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.view.Observer;

import java.util.Timer;


public class Game implements Observer {

    private Player[] players = new Player[5];
    private Turn turnOf;
    private Table gameboard ;
    private Timer timer;
    private String gamemode;
    private State state;
    private Player firstPlayer;
    private String[] mapconfig = new String[3];

    public void newPlayer(String nickname){

    }

    public void selectGameboard(String type ){

    }

    public void setGamemode(String type){

    }

    public String[] listGameboard(){
        return mapconfig;
    }

    public String listGamemode(){
        return gamemode;
    }

    public Player getFirstPlayer(){
        return firstPlayer;
    }

    public State getState(){
        return state;
    }

    private void loadMap(Integer maptype){

    }

    private void initializeWeaponDeck(){

    }

    private void initializePowerUpDeck(){

    }

    private void initializeAmmoDeck(){

    }

    private void initializeTable(){

    }
}
