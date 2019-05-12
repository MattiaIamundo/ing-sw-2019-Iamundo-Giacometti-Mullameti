package it.polimi.sw2019.model;

import it.polimi.sw2019.view.TableView;

import java.io.Serializable;

/**Class Table
 * @author Merita Mullameti
 */

public class Table extends TableView implements Cloneable, Serializable {

    private Skull[] killshotTrack = new Skull[8];
    //the map of the battlefield ;there can be 3 different ones , based on the number of the players
    private Map map;
    private Weapon[] weaponDeck = new Weapon[21];
    private PowerUp[] powerupDeck = new PowerUp[24];
    private Ammo[] ammoDeck = new Ammo[36];
    //there can be 3 , 4 or 5 players
    private static Player[] players = new Player[5];
    //this variable indicates the player who is currently playing
    private static Player currentPlayer;

    /**
     * Constructor of the class
     * @param map the map of the battlefield
     * @param killshotTrack the track of skulls
     */

    public Table(Map map , Skull[] killshotTrack ){

        this.map=map;
        this.killshotTrack = killshotTrack.clone();

    }

    /**
     * this method has to show to the view the table
     */
    protected void showTable (Table message){

    }



    /**
     * @return the Track of skulls
     */
    public Skull[] getKillshotTrack(){
        return killshotTrack;
    }
    /**
     * @return the power up Deck
     */
    public PowerUp[] getPowerUp(){
        return powerupDeck;
    }
    /**
     * @return the weapon Deck
     */
    public Weapon[] getWeapon(){
        return weaponDeck;
    }
    /**
     * @return the ammo Deck
     */
    public Ammo[] getAmmo(){
        return ammoDeck;
    }
    /**
     * @return one of the players
     */
    public static Player getPlayers(int i){
        return players[i];
    }
    /**
     * @return the current player
     */
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }
    /**
     * @return the current player
     */
    public static Player setCurrentPlayer(){
        return currentPlayer;
    }
}
