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
    //the map of the battlefield ;there can be 3 different ones , based on the number of the players
    private Map map;
    private String nrSkulls;
    private String Map;
    private ArrayList<Weapon> weaponDeck; //21
    private ArrayList<PowerUp> powerupDeck; //24
    private ArrayList<Ammo> ammoDeck; //36


    public Table( ){

        killshotTrack = new ArrayList<>(8);
        weaponDeck = new ArrayList<>(21);
        powerupDeck = new ArrayList<>(24);
        ammoDeck = new ArrayList<>(36);
        List<Space> list1 = new ArrayList<>(3);
        List<Space> list2 = new ArrayList<>(3);
        List<Space> list3 = new ArrayList<>(3);
        List<Space> list4 = new ArrayList<>(3);

        map = new Map( list1, list2, list3, list4);

    }

    /**
     * this method has to show to the view the table
     */
    @Override
    protected void showTable (Table message){

    }

    /**
     * @return the Track of skulls
     */
    public List<Skull> getKillshotTrack(){
        return killshotTrack;
    }

    public void setMap(Map mappa) {
        map = mappa;
    }

    public Map getMap() {
        return map;
    }

    /**
     * @return the power up Deck
     */
    public List<PowerUp> getPowerUp(){
        return powerupDeck;
    }

    /**
     * @return the weapon Deck
     */
    public List<Weapon> getWeapon(){
        return weaponDeck;
    }

    /**
     * @return the ammo Deck
     */
    public List<Ammo> getAmmo(){
        return ammoDeck;
    }

    public String getNrMap() {
        return Map;
    }

    public void setNrMap(String nrMap) {
        this.Map=nrMap;
    }


    public String getNrSkulls() {
        return nrSkulls;
    }

    public void setNrSkulls(String nrSkulls) {
        this.nrSkulls=nrSkulls;
    }

}
