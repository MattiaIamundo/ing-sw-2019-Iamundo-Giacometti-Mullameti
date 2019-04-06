package it.polimi.sw2019.model;

import it.polimi.sw2019.view.TableView;

public class Table extends TableView implements Cloneable {

    private Skull[] killshotTrack = new Skull[8];
    private Space[][] map = new Space[3][4];
    private Weapon[] weaponDeck = new Weapon[21];
    private PowerUp[] powerupDeck = new PowerUp[24];
    private Ammo[] ammoDeck = new Ammo[36];

    public Skull[] getKillshotTrack(){
        return killshotTrack;
    }

    public PowerUp[] getPowerUp(){
        return powerupDeck;
    }

    public Weapon[] getWeapon(){
        return weaponDeck;
    }

    public Ammo[] getAmmo(){
        return ammoDeck;
    }
}
