package it.polimi.sw2019.controller;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.sw2019.events.client_event.Cevent.Login;
import it.polimi.sw2019.events.client_event.Cevent.Reconnection;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.network.Socket.PlayerThread;
import it.polimi.sw2019.utility.TimerThread;
import it.polimi.sw2019.view.ObservableByGame;
import it.polimi.sw2019.view.Observer;
import it.polimi.sw2019.view.PlayerRemoteView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game implements Observer <ObservableByGame> {

    //the model
    private final ArrayList<Player> players;
    private final Table gameboard;
    //the utility
    private TimerThread timerThread;
    //for the synchronization
    private Object stop = new Object();
    private Object stopArray = new Object();
    //the main controller's variables
    private final Turn turnOf;
    private String gamemode;
    private State state;
    private String firstPlayer;
    private String[] mapconfig;
    private boolean out;
    private boolean gameover;
    private boolean gameStarted;
    //to load milliseconds to the timer
    private Gson gson = new Gson();
    private static final Logger logger = Logger.getLogger( Game.class.getName() );

    //------ Game's Methods ------

    public Game() {

        players  = new ArrayList<>(5);
        turnOf = new TurnNormal(null, 0, null);
        gameboard = new Table(null, null);
        gamemode = "null";
        state = new State();
        firstPlayer = "null";
        timerThread = new TimerThread();
        mapconfig = new String[3];
        out = false;
        gameover = false;

        try{
            FileReader reader = new FileReader("File_Json/milliSecondsToTimerBeginning.json");
            timerThread.setTime(gson.fromJson( reader , int.class));
        } catch (IOException ee) {
            logger.log(Level.INFO, "{Game} IOException!\n" + ee.toString());
        }
    }

    public synchronized void setTimerThreadToTheGame() {

        try{
            FileReader reader = new FileReader("File_Json/milliSecondsToTimerTurnOfGame.json");
            timerThread.setTurnTime(gson.fromJson( reader , int.class));

        } catch ( FileNotFoundException e){
            logger.log(Level.INFO, "{Game} File not found!\n");
        }
        //also in the timer
    }

    public void setOut(boolean status) {
        this.out = status;
    }

    public boolean getOut() {
        return this.out;
    }

    public synchronized List<Player> getPlayers() {
        return players;
    }

    public synchronized TimerThread getTimerThread() {
        return timerThread;
    }

    public synchronized boolean getGameover() {
        return gameover;
    }

    public synchronized void setGameover(boolean status) {
        this.gameover = status;
    }

    public synchronized void setGameStarted(boolean status) {
        this.gameStarted = status;
    }

    public synchronized boolean getGameStarted() {
        return this.gameStarted;
    }

    public synchronized void addPlayers(String nickname) {
        Player player = new Player(nickname, 0, null, null);
        players.add(player);
        logger.log(Level.INFO, "{Game} has added a player!\n");
    }

    public synchronized void askForNickname( PlayerRemoteView prv, boolean firstTime, List<Player> list) {

        List<String> namelist = new ArrayList<>(5);

        if( !list.isEmpty() ){

            for( Player y : list ) {
                namelist.add( y.getNickname() );
            }
        }
        Login login = new Login(firstTime, namelist);
        prv.requestNickname(login);

    }

    public synchronized void sendReconnection(boolean firstTime, PlayerRemoteView prv) {
        Reconnection rer = new Reconnection(firstTime, "Reconnection");
        prv.requestNickname(rer);
    }

    public void sendGoodbye(PlayerRemoteView prv) {
        prv.sendGoodbye();
    }

    public void sendOk(PlayerRemoteView prv) {
        prv.sendOk();
    }

    public synchronized Object getStop() {
        return this.stop;
    }

    public synchronized Object getStopArray() {
        return stopArray;
    }

    public void sendPing(PlayerRemoteView prv) {
        prv.sendPing();
    }

    public synchronized  void removePlayers(PlayerThread pt) {

        Iterator<Player> it = players.iterator();

        while( it.hasNext() ) {

            Player p = it.next();
            if ( p.getNickname().equals(pt.getNickname()) ) {
                it.remove();
                break;
            }
        }
    }

    public void setGameStart() {

        //here how to set the game!
        System.out.println("setting the game!\n");
    }


    //.............................FROM HERE OLD THINGS........................

    public void update (ObservableByGame message) {

        if ( message instanceof Ammo ) {

            //i have to pass the object to the remoteView

        }
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

    public String getFirstPlayer(){
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


  //  public void setTurnOfPlayer (Player player) { this.turnOf.setPlayer(player); }

    public Player getTurnOfPlayer () {
        return turnOf.getPlayer();
    }
}
