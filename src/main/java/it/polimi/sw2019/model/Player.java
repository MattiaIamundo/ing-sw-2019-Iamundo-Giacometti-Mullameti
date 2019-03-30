package it.polimi.sw2019.model;


public class Player {

    private String nickname;
    private Integer score;
    private Integer[] ammo = new Integer[3];
    private Weapon[] weapon = new Weapon[3];
    private Space position;
    private Player[] marked = new Player[3];
    public PlayerPlance plance;
    private PowerUp[] powerup = new PowerUp[3];

    public String getNickname(){

    }
    public Integer getScore() {

    }
    public void takeAmmo() {

    }
    public Space getPosition() {

    }
    public boolean isVisible() {

    }
    public PlayerPlance getPlance() {

    }

    public Weapon[] listWeapon() {

    }
    public PowerUp[] listPowerUp() {

    }
}
