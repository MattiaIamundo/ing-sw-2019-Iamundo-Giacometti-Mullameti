package it.polimi.sw2019.model;

import it.polimi.sw2019.view.PlayerView;

import java.util.ArrayList;

/**Class Player : describes player's characteristics
 * @author Merita Mullameti
 */
public class Player extends PlayerView implements Cloneable {

    private String nickname; //player's nickname
    private Integer score; //player's score
    //first red, second blue, third yellow
    private Integer[] ammo = new Integer[3];
    //list of weapons the player owns , every player can own at top 3 weapons
    //if null there isn't a weapon in that position
    private Weapon[] weapon = new Weapon[3];

    private Space position; //the position the player's in
    private ArrayList<Player> marked = new ArrayList<>();
    private PlayerPlance plance;
    //list of powerup cards  the player ownes , every player can own at top 3 weapons
    private PowerUp[] powerup = new PowerUp[3];

    /**Constructor of the class
     * @param nickname
     * @param score
     * @param position
     * @param plance
     */
    public Player(String nickname , Integer score , Space position, PlayerPlance plance){
        this.nickname=nickname;
        this.score=score;
        this.position=position;
        this.plance=plance;
    }
    /**
     * @return the player's nickname
     */
    public String getNickname(){
        return nickname;
    }
    /**
     * @return the player's score
     */
    public Integer getScore() {
        return score;
    }
    /**
     * @return the player's position
     */
    public Space getPosition() {
        return position;
    }
    /**
     * @return true if this Player is visible from the caller Player
     */
    public boolean isVisible(Player callerPlayer) {
        return true;
    }
    /**
     * @return the player's plance
     */
    public PlayerPlance getPlance() {
        return plance;
    }
    /**
     * @return a pointer to the list of weapons the player ownes
     */
    public Weapon[] listWeapon() { return weapon; }
    /**
     * @return a pointer to the list of powerup the player ownes
     */
    public PowerUp[] listPowerUp() {
        return powerup;
    }

    /**
     * @return the player's ammo
     */
    public Integer[] getAmmo() {
        return ammo;
    }

    /**
     * change the actual position of the player
     * @param position identifies the new position of he Player
     */
    public void setPosition(Space position) {
        this.position = position;
    }
}
