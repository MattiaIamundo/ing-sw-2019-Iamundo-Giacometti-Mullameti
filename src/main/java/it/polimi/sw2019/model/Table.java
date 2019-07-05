package it.polimi.sw2019.model;

import it.polimi.sw2019.view.TableView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**Class Table
 * @author Merita Mullameti
 */

public class Table extends TableView implements Cloneable, Serializable {

    private ArrayList<Skull> killshotTrack;
    //the map of the battlefield ;there can be 4 different ones , based on the choose of the first player
    private Map map;
    private String nrSkulls;
    private String Map;
    private ArrayList<Weapon> weaponDeck; //21
    private ArrayList<PowerUp> powerupDeck; //24
    private ArrayList<Ammo> ammoDeck; //36

    /**
     * the constructor: set not null the decks and the map
     */
    public Table( ){

        killshotTrack = new ArrayList<>(8);
        weaponDeck = new ArrayList<>(21);
        powerupDeck = new ArrayList<>(24);
        ammoDeck = new ArrayList<>(36);
        Map = "null";
        nrSkulls = "null";
        List<Space> list1 = new ArrayList<>(3);
        List<Space> list2 = new ArrayList<>(3);
        List<Space> list3 = new ArrayList<>(3);
        List<Space> list4 = new ArrayList<>(3);

        map = new Map( list1, list2, list3, list4);

    }

    /**
     * skull's track getter
     * @return the Track of skulls
     */
    public List<Skull> getKillshotTrack(){
        return killshotTrack;
    }

    /**
     * set the map
     * @param mappa the new map's id
     */
    public void setMap(Map mappa) {
        map = mappa;
    }

    /**
     * the map's id getter
     * @return the map's id
     */
    public Map getMap() {
        return map;
    }

    /**
     * the power up deck's getter
     * @return the power up Deck
     */
    public List<PowerUp> getPowerUp(){
        return powerupDeck;
    }

    /**
     * the weapon deck's getter
     * @return the weapon Deck
     */
    public List<Weapon> getWeapon(){
        return weaponDeck;
    }

    /**
     * the ammo desk's getter
     * @return the ammo Deck
     */
    public List<Ammo> getAmmo(){
        return ammoDeck;
    }

    /**
     * the map's id getter
     * @return the map's id
     */
    public String getNrMap() {
        return Map;
    }

    /**
     * the map's id setter
     * @param nrMap map id
     */
    public void setNrMap(String nrMap) {
        this.Map=nrMap;
    }

    /**
     * the skull's number getter
     * @return the skulls' number
     */
    public String getNrSkulls() {
        return nrSkulls;
    }

    /**
     * the number of skulls setter
     * @param nrSkulls the number of skull
     */
    public void setNrSkulls(String nrSkulls) {
        this.nrSkulls=nrSkulls;
    }

    /**
     * this methods clones the table
     * @return the table's clone
     * @throws CloneNotSupportedException if the table is not serializable
     */
    public Object cloneTable() throws CloneNotSupportedException{
        return super.clone();
    }

}
