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
    //list of power up cards  the player owns , every player can own at top 3 weapons
    private PowerUp[] powerup = new PowerUp[3];

    /**Constructor of the class
     * @param nickname the player's nickname
     * @param score the player's score in this game
     * @param position the player's position in the map
     * @param plance hte player's plance
     */
    public Player(String nickname , Integer score , Space position, PlayerPlance plance){
        this.nickname = nickname;
        this.score = score;
        this.position = position;
        this.plance = plance;
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
     * the callerPlayer is not this player
     * @return true if this Player is visible from the caller Player
     */
    public boolean isVisible(Player callerPlayer) {
        //the players are in the same room
        if (this.getPosition().getRoom() == callerPlayer.getPosition().getRoom()) {
            return true;
        } else {
            //the players are in different rooms
            if (!(callerPlayer.getPosition().getNorth().isWall())) {
                if (callerPlayer.getPosition().getNorth().getSpaceSecond().getRoom() == this.getPosition().getRoom()) {
                    return true;
                }
            } else if (!(callerPlayer.getPosition().getSouth().isWall())) {
                if (callerPlayer.getPosition().getSouth().getSpaceSecond().getRoom() == this.getPosition().getRoom()) {
                    return true;
                }
            } else if (!(callerPlayer.getPosition().getEast().isWall())) {
                if (callerPlayer.getPosition().getEast().getSpaceSecond().getRoom() == this.getPosition().getRoom()) {
                    return true;
                }
            } else if (!(callerPlayer.getPosition().getWest().isWall())) {
                if (callerPlayer.getPosition().getWest().getSpaceSecond().getRoom() == this.getPosition().getRoom()) {
                    return true;
                }
            }
        }
        return false;
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
     * @param position identifies the new position of the player
     */
    public void setPosition(Space position) {
        this.position = position;
    }
}
