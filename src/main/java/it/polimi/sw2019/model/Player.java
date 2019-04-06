package it.polimi.sw2019.model;


import it.polimi.sw2019.view.PlayerView;

import java.util.ArrayList;

public class Player extends PlayerView implements Cloneable {

    private String nickname;
    private Integer score;
    private Integer[] ammo = new Integer[3]; //first red, second blue, third yellow
    private Weapon[] weapon = new Weapon[3];
    private Space position;
    private ArrayList<Player> marked = new ArrayList<>();
    public PlayerPlance plance;
    private PowerUp[] powerup = new PowerUp[3];

    public String getNickname(){

    }
    public Integer getScore() {

    }

    public Space getPosition() {

    }
    public boolean isVisible() {   //return true if this Player is visible from the caller Player

    }
    public PlayerPlance getPlance() {

    }

    public Weapon[] listWeapon() {

    }
    public PowerUp[] listPowerUp() {

    }
}
