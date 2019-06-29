package it.polimi.sw2019.model;


import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.exception.WeaponOutOfBoundException;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.ObservableByGame;
import it.polimi.sw2019.view.Observer;
import it.polimi.sw2019.view.PlayerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

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
    //the opponents who the player has marked
    private ArrayList<Player> marked = new ArrayList<> (12);
    private PlayerPlance plance;
    //list of power up cards  the player owns , every player can own at top 3 weapons
    private PowerUp[] powerup = new PowerUp[3];
    //
    private int playerNumber;
    //
    private boolean connected;

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
        this.score = this.score + points;
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
     * this method set the marks which the player gave to the others players
     * @param markedPlayer the player who is marked
     * @param numberOfMarks the number of mark given
     */
    public void addMarked(Player markedPlayer, int numberOfMarks) {
        //the arrayList is empty and the number of marks is ok
        if( marked.isEmpty() && numberOfMarks < 12 ){

            for (int i = 0; i < numberOfMarks; i++){

                marked.add(markedPlayer);
            }
        }
        //the arrayList is not empty but the number of marks is not big enough
        else if ( !marked.isEmpty() && (marked.size() + numberOfMarks) < 12 ){

            for ( int i = marked.size() + 1; i < marked.size() + numberOfMarks; i++) {

                marked.add(markedPlayer);
            }
        }
        //the arrayList is not empty but the number of marks is big enough to create a problem
        else if ( !marked.isEmpty() && (marked.size() + numberOfMarks) > 12 ){

            for (int i = marked.size() + 1; i < 12 ; i++){

                marked.add(markedPlayer);
            }
        }
    }

    /**
     * this method remove the marks which are transformed in damages
     * @param markedPlayer the player who is damaged and has got also some of attacker's marks
     */
    public void removeMarked (Player markedPlayer){

        Iterator <Player> iterator = marked.iterator();
        //there is almost one marked
        if ( !marked.isEmpty() ){

            while (iterator.hasNext()){

                Player p = iterator.next();
                //if the arrayList marked contains the markedPlayer, it is to be removed
                if (p == markedPlayer){

                    iterator.remove();
                }
            }
        }
        else{
            System.out.println("The player didn't marked any players!\n ");
        }
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
    public PowerUp[] listPowerUp() {
        return powerup;
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
            if (ammocard.getColorFirst() == "red") {

                if(ammo[0]>=3){

                }else{
                    ammo[0]++;
                }



            } else if (ammocard.getColorFirst() == "blue") {

                if(ammo[1]>=3){

                }else{
                    ammo[1]++;
                }

            } else if (ammocard.getColorFirst() == "yellow") {

                if(ammo[2]>=3){

                }else{
                    ammo[2]++;
                }

            }

            //add the second ammo in the ammo array

            if(ammocard.getColorFirst()=="red"){

                if(ammo[0]>=3){

                }else{
                    ammo[0]++;
                }

            }else if(ammocard.getColorFirst()=="blue"){

                if(ammo[1]>=3){

                }else{
                    ammo[1]++;
                }

            }else if (ammocard.getColorFirst()=="yellow"){

                if(ammo[2]>=3){

                }else{
                    ammo[2]++;
                }

            }


        if (ammocard instanceof AmmoTriple) {


                //add the third ammo in the ammo array

                if(((AmmoTriple) ammocard).getColorThird()=="red"){

                    if(ammo[0]>=3){

                    }else{
                        ammo[0]++;
                    }

                }else if(((AmmoTriple) ammocard).getColorThird()=="blue"){

                    if(ammo[1]>=3){

                    }else{
                        ammo[1]++;
                    }

                }else if((((AmmoTriple) ammocard).getColorThird()=="yellow")){

                    if(ammo[2]>=3){

                    }else{
                        ammo[2]++;
                    }

                }


        }
        else if (ammocard instanceof AmmoDoublePowerUp) {

            if (powerup[2]!=null) {
                System.out.println("You already have three powerup cards !!");
                System.out.println("Don't pick up a new power up card");
            }else{
                for (int i = 0; i < 3; i++) {
                    if (powerup[i] == null) {

                        powerup[i] = ((AmmoDoublePowerUp) ammocard).getPowerUp();
                        break;
                    }
                }
            }

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

}


