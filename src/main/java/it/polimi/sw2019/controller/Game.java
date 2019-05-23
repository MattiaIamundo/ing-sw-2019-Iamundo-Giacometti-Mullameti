package it.polimi.sw2019.controller;


import it.polimi.sw2019.model.Ammo;
import it.polimi.sw2019.view.ObservableByGame;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.view.Observer;

import static it.polimi.sw2019.view.PlayerRemoteView.nickname;
import static it.polimi.sw2019.view.PlayerRemoteView.timer;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;


public class Game implements Observer <ObservableByGame> {
    //the max number of players is 5
    private static ArrayList<Player> players = new ArrayList<>(5);
    private Turn turnOf = new Turn(0,null);
    private Table gameboard;
    private Timer timer;
    private String gamemode = null;
    private State state;
    private Player firstPlayer;
    private String[] mapconfig = new String[3];
    private static int secondToTimer = 0;

    public void update (ObservableByGame message) {

        if ( message instanceof Ammo ) {

            //i have to pass the object to the remoteView

        }
    }

    /**
     * this is a selection method
     * call the remoteView method nickname to request the nickname player
     * and player's number
     */
    public void addPlayer(Socket socket){

        nickname(socket);
    }

    /**
     * this method control about the player's nickname if it is correct or not
     * if yes assign it to the player
     * @param name the players' nickname
     */
    public void nicknameReturn (String name) {

        if ( players.isEmpty() ) {

            Player player = new Player(name, 0, null, null);
            players.add(player);
        }
        else {

            Iterator <Player> it = players.iterator();
            boolean present = false;

            while ( it.hasNext() ) {

                Player p = it.next();
                if ( p.getNickname().equals(name)) {
                    //there is already this nickname
                    present = true;
                    break;
                }
            }
            if ( present ) {
                //send another request
            }
            else {
                Player p = new Player(name, 0, null, null);
                players.add( p );

            }
        }


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

    public void setTimer() {
        timer();
    }

    public static void addTimer(int milliseconds) {

        secondToTimer = milliseconds;
    }

    public int getSecondToTimer () {
        return secondToTimer;
    }

    public void setTurnOfPlayer (Player player) {

        this.turnOf.setPlayer(player);
    }

    public Player getTurnOfPlayer () {

        return this.turnOf.getPlayer();
    }
}
