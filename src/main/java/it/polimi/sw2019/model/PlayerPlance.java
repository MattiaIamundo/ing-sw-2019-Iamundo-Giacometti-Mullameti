package it.polimi.sw2019.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * this class stands for the player's plance
 * @author Luca Giacometti
 */
public class PlayerPlance {

    //the vector is set to 12 space due to every player can be hit at top 12 times
    private String[] damageTrack = new String[12];
    private boolean isFirstAdrenaline;
    private static boolean isSecondAdrenaline;
    private boolean isKilled;
    private boolean isOverkilled;
    //this vector shows how many times the player is dead
    //so the controller will be able to calculate the points
    private boolean[] killed = new boolean[6];
    //the initial capacity is set to 12 since every player could be marked at top 12 times
    private ArrayList<Player> markedBy;

    /**
     * this is the constructor
     */
    public PlayerPlance (){
        this.isFirstAdrenaline = false;
        this.isKilled = false;
        this.isSecondAdrenaline = false;
        this.isOverkilled = false;
    }

    /**
     * @return a vector where is set how many times the player was killed
     */
    public boolean[] getKilled () {

        return killed;
    }
    /**
     * @return true if the player got the first adrenaline
     */
    public boolean getFirstAdrenaline(){
        return isFirstAdrenaline;
    }
    /**
     * @return true if the player got the second adrenaline
     */
    public static boolean getSecondAdrenaline(){
        return isSecondAdrenaline;
    }
    /**
     * @return true if the player is dead
     */
    public boolean getIsKilled(){
        return isKilled;
    }
    /**
     * @return true if the player is raged
     */
    public boolean getOverkilled(){
        return isOverkilled;
    }
    /**
     * @param shooter it is the opponent who provoked the damage
     * @param quantity it is the amount of damage
     */
    public void giveDamage(Player shooter, Integer quantity){

        int i = 0;
        while(quantity > 0 && i < 12) {

            if (damageTrack[i] != null) {
                i++;
            }
            else {
                this.damageTrack[i] = shooter.getNickname();
                quantity--;
            }
        }
        if (damageTrack[11] != null && quantity > 0) {

            System.out.println("The player is already overkilled, you can't damage him again...\n");
        }
    }
    /**
     * @param shooter it is the opponent who marked the player
     */
    public void setMark(Player shooter) {
        //the arrayList is empty
        if ( markedBy.isEmpty() ) {
            markedBy.add( 0,shooter);
        }
        //the arrayList is not full
        else if ( (markedBy.size()) < 12 ) {
            //the arrayList is not full
            markedBy.add(shooter);
        }
        else {
            System.out.println("The target player's mark list is already full...\n");
        }
    }

    /**
     * this method remove marks given by "shooter" player
     * @param shooter the marks of this player has to be removed
     */
    public void removeMark(Player shooter){

        Iterator <Player> iterator = markedBy.iterator();
        //the arrayList is not empty
        if ( !markedBy.isEmpty() ){

            while ( iterator.hasNext() ){

                Player p = iterator.next();
                //if in the list there are some marks of the "shooter" player, those have to be removed
                if( p == shooter){

                    iterator.remove();
                }
            }
        }
        else{
            System.out.println("The player is not marked by anybody!\n ");
        }
    }
}