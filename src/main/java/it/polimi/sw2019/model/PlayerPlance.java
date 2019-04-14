package it.polimi.sw2019.model;

import java.util.ArrayList;
/**
 * this class stands for the player's plance
 */
public class PlayerPlance {

    //the vector is set to 12 space due to every player can be hit at top 12 times
    private String[] damageTrack = new String[12];
    private boolean isFirstAdrenaline;
    private boolean isSecondAdrenaline;
    private boolean isKilled;
    private boolean isOverkilled;
    //this vector shows how many times the player is dead
    //so the controller will be able to calculate the points
    private boolean[] killed = new boolean[6];
    //the initial capacity is set to 12 since every player could be marked at top 12 times
    private ArrayList<Player> markedBy = new ArrayList<>(12);

    /**
     * @author Luca Giacometti
     * this is the constructor
     */
    public PlayerPlance (){
        this.isFirstAdrenaline = false;
        this.isKilled = false;
        this.isSecondAdrenaline = false;
        this.isOverkilled = false;
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
    public boolean getSecondAdrenaline(){
        return isSecondAdrenaline;
    }
    /**
     * @return true if the player is dead
     */
    public boolean getKilled(){
        return isKilled;
    }
    /**
     * @return true if the player is raged
     */
    public boolean getOverkilled(){
        return isOverkilled;
    }
    /**
     * @author Luca Giacometti
     * @param shooter it is the opponent who provoked the damage
     * @param quantity it is the amount of damage
     * the controller has to control if the vector is full
     */
    public void giveDamage(Player shooter, Integer quantity){
        int i = 0;
        while(quantity > 0 && i < 12) {
            if (damageTrack[i] != null) {
                i++;
            } else {
                this.damageTrack[i] = shooter.getNickname();
                quantity--;
            }
        }
    }
    /**
     * @author Luca Giacometti
     * @param shooter it is the opponent who marked the player
     * the controller has to control if the arraylist is full
     */
    public void setMark(Player shooter) {
        if ( markedBy.isEmpty() ) {
            markedBy.add( 0,shooter);
        }
        else {
            markedBy.add( markedBy.lastIndexOf(null), shooter);
        }
    }
}