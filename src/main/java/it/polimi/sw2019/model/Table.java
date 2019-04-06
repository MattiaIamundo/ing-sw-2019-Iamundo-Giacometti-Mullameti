package it.polimi.sw2019.model;

import it.polimi.sw2019.view.TableView;

public class Table extends TableView implements Cloneable {

    private Skull[] killshotTrack = new Skull[8];
    private Space[][] map = new Space[3][4];
    private Weapon[] weaponDeck = new Weapon[21];
    private PowerUp[] powerupDeck = new PowerUp[24];
    private Ammo[] ammoDeck = new Ammo[36];

    public Skull[] getKillshotTrack(){

    }

    public PowerUp getPowerUp(){

    }

    public Weapon getWeapon(){

    }

    public Ammo getAmmo(){

    }
}
