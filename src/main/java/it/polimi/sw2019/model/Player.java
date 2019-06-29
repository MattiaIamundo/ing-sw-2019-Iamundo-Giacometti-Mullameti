package it.polimi.sw2019.model;


import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.exception.PowerUpOutOfBoundException;
import it.polimi.sw2019.exception.WeaponOutOfBoundException;
import it.polimi.sw2019.view.ObservableByGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Class Player : describes player's characteristics
 * @author Merita Mullameti
 */
public class Player extends ObservableByGame implements Cloneable, Serializable {

    private String nickname; //player's nickname
    private int score; //player's score
    private String character = "null";
    //first red, second blue, third yellow
    private int[] ammo = new int[3];
    //list of weapons the player owns , every player can own at top 3 weapons
    private ArrayList<Weapon> weapons = new ArrayList<>();
    //the position the player's in
    private Space position;
    private PlayerPlance plance;
    //list of power up cards  the player owns , every player can own at top 3 weapons
    private  ArrayList<PowerUp> powerup = new ArrayList<>();
    //
    private int playerNumber;
    //
    private boolean connected;
    private ArrayList<Player> lastHittedPlayers = new ArrayList<>();

    /**Constructor of the class
     * @param nickname the player's nickname
     * @param score the player's score in this game
     * @param position the player's position in the map
     * @param plance the player's plance
     */
    //to add color
    public Player(String nickname , int score , Space position, PlayerPlance plance){
        this.nickname = nickname;
        this.score = score;
        this.position = position;
        this.plance = plance;
    }

    protected void showPlayer() {

    }

    /**
     * @return the player's nickname
     */
    public String getNickname(){
        return nickname;
    }

    public void setNickname(String name) {
        this.nickname = name;
    }

    /**
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * this method adds some points or a point to the player's score
     * @param points the points which the player has to gain
     */
    public void addPoints(int points) {
        score = score + points;
    }

    /**
     * @return the player's position
     */
    public Space getPosition() {
        return position;
    }

    /**
     * the callerPlayer is not this player
     * @return true if this.player is visible from the caller Player
     */
    public boolean isVisible(Player callerPlayer) {
        //the players are in the same room
        if (this.getPosition().getRoom().equals(callerPlayer.getPosition().getRoom())) {
            return true;
        } else {
            //the players are in different rooms
            if (!(callerPlayer.getPosition().getNorth().isWall())) {
                if (callerPlayer.getPosition().getNorth().getSpaceSecond().getRoom().equals(this.getPosition().getRoom())) {
                    return true;
                }
            }
            if (!(callerPlayer.getPosition().getSouth().isWall())) {
                if (callerPlayer.getPosition().getSouth().getSpaceSecond().getRoom().equals(this.getPosition().getRoom())) {
                    return true;
                }
            }
            if (!(callerPlayer.getPosition().getEast().isWall())) {
                if (callerPlayer.getPosition().getEast().getSpaceSecond().getRoom().equals(this.getPosition().getRoom())) {
                    return true;
                }
            }
            if (!(callerPlayer.getPosition().getWest().isWall())) {
                if (callerPlayer.getPosition().getWest().getSpaceSecond().getRoom().equals(this.getPosition().getRoom())) {
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
     * @return the list of the player's weapons
     */
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * This method return a specific weapon
     * @param name The name of the searched weapon
     * @return the searched weapon
     * @throws InexistentWeaponException if the player doesn't have the specified weapon or if the weapon's name is incorrect
     */
    public Weapon getWeapon (String name) throws InexistentWeaponException {
        for (Weapon weapon : weapons){
            if (weapon.getName().equals(name)){
                return weapon;
            }
        }
        throw new InexistentWeaponException(this.nickname);
    }

    /**
     * This method add a weapon to the player's weapons list
     * @param weap the weapon to be added
     * @throws WeaponOutOfBoundException if the player already have 3 weapon
     */
    public void addWeapon(Weapon weap) throws WeaponOutOfBoundException {
        if (weapons.size() == 3){
            throw new WeaponOutOfBoundException(this.nickname);
        }else {
            weapons.add(weap);
        }
    }

    /**
     * This method remove a weapon from the player's weapons list
     * @param weap the weapon that have to be removed
     */
    public void removeWeapon(Weapon weap) {
        weapons.remove(weap);
    }

    /**
     * @return a pointer to the list of powerup the player ownes
     */
    public ArrayList<PowerUp> getPowerup() {
        return powerup;
    }

    public void addPowerUp(PowerUp powerUp) throws PowerUpOutOfBoundException {
        if (powerup.size() <= 3){
            powerup.add(powerUp);
        }else {
            throw new PowerUpOutOfBoundException(this.getNickname());
        }
    }

    public void removePowerUp(PowerUp powerUp){
        powerup.remove(powerUp);
    }

    /**
     * @return the player's ammo
     */
    public int[] listAmmo() {
        return ammo;
    }

    /**
     * change the actual position of the player
     * @param position identifies the new position of the player
     */
    public void setPosition(Space position) {
        this.position = position;
    }

    public void setPlayerNumber (int number) {
        this.playerNumber = number;
    }

    public int getPlayerNumber () {
        return this.playerNumber;
    }

    public void addAmmo(Ammo ammocard){

        //add the first ammo in the ammo array
        loadAmmo(ammocard.getColorFirst());

        //add the second ammo in the ammo array
        loadAmmo(ammocard.getColorSecond());
        if (ammocard instanceof AmmoTriple) {

            //add the third ammo in the ammo array
            loadAmmo(((AmmoTriple) ammocard).getColorThird());
        }
        else if (ammocard instanceof AmmoDoublePowerUp) {
            Logger logger = Logger.getLogger("model.Player.addAmmo.AmmoDoublePowerUp");

            try {
                addPowerUp(((AmmoDoublePowerUp) ammocard).getPowerUp());
            }catch (PowerUpOutOfBoundException e){
                logger.log(Level.WARNING, e.getMessage()+" already have 3 PowerUps");
            }
        }
    }

    private void loadAmmo(String color){
        switch (color){
            case "red":
                if (ammo[0] < 3){
                    ammo[0]++;
                }
                break;
            case "blue":
                if (ammo[1] < 3){
                    ammo[1]++;
                }
                break;
            case "yellow":
                if (ammo[2] < 3){
                    ammo[2]++;
                }
                break;
            default:
                break;
        }
    }

    public int[] getAmmo() {
        return this.ammo;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public ArrayList<Player> getLastHittedPlayers() {
        return lastHittedPlayers;
    }
}


