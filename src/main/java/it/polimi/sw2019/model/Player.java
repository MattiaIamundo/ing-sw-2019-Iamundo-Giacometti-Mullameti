package it.polimi.sw2019.model;

import it.polimi.sw2019.view.PlayerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**Class Player : describes player's characteristics
 * @author Merita Mullameti
 */
public class Player extends PlayerView implements Cloneable, Serializable {

    private String nickname; //player's nickname
    private int score; //player's score
    //first red, second blue, third yellow
    private static int[] ammo = new int[3];
    //list of weapons the player owns , every player can own at top 3 weapons
    //if null there isn't a weapon in that position
    private Weapon[] weapon = new Weapon[3];

    private Space position; //the position the player's in
    //the opponents who the player has marked
    private ArrayList<Player> marked;
    private PlayerPlance plance;
    //list of power up cards  the player owns , every player can own at top 3 weapons
    private static PowerUp[] powerup = new PowerUp[3];

    /**Constructor of the class
     * @param nickname the player's nickname
     * @param score the player's score in this game
     * @param position the player's position in the map
     * @param plance the player's plance
     */
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
     * @return a pointer to the list of weapons the player owns
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


    public static void addAmmo(Ammo ammocard){
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

}


