package it.polimi.sw2019.controller;


import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.view.ObservableByGame;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.view.Observer;

import static it.polimi.sw2019.view.AmmoView.showAmmo;

import java.util.ArrayList;
import java.util.Timer;


public class Game implements Observer <ObservableByGame> {
    //the max number of players is 5
    private ArrayList<Player> players = new ArrayList<>(5);
    private Turn turnOf;
    private Table gameboard ;
    private Timer timer;
    private String gamemode;
    private State state;
    private Player firstPlayer;
    private String[] mapconfig = new String[3];


    public void update (ObservableByGame message) {

        if ( message instanceof Ammo ) {

            //i have to pass the object to the remoteView

        }
    }

    public void showTheAmmo (Ammo ammo) {

        showAmmo(ammo);
    }


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

    private void loadMap(int maptype){

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
