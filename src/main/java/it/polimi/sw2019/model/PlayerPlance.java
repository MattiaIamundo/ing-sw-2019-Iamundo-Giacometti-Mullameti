package it.polimi.sw2019.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this class stands for the player's plance
 * @author Luca Giacometti
 */
public class PlayerPlance implements Cloneable, Serializable {

    //the vector is set to 12 space due to every player can be hit at top 12 times
    private List<String> damageTrack = new ArrayList<>(12);
    private boolean isFirstAdrenaline;
    private boolean isSecondAdrenaline;
    private boolean isKilled;
    private boolean isOverkilled;
    //this vector shows how many times the player is dead
    //so the controller will be able to calculate the points
    private boolean[] killed = new boolean[6];
    //the initial capacity is set to 12 since every player could be marked at top 12 times
    private ArrayList<Player> markedBy = new ArrayList <> ();

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
    public boolean getSecondAdrenaline(){
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
     * this method set the damages to the player
     * @param shooter it is the opponent who provoked the damage
     * @param quantity it is the amount of damage
     */
    public void giveDamage(Player shooter, Integer quantity){

        while((quantity > 0) && (damageTrack.size() <= 12)) {
            damageTrack.add(shooter.getNickname());
            quantity--;
        }
    }

    /**
     * @param shooter it is the opponent who marked the player
     */
    public void setMark(Player shooter) {
        if (Collections.frequency(markedBy, shooter) < 3){
            markedBy.add(shooter);
        }
    }

    /**
     * this method remove marks given by "shooter" player and assign it as damage
     * @param shooter the marks of this player has to be removed
     */
    public void removeMark(Player shooter){
        int count = 0;
        for (Player player : markedBy){
            if (player == shooter){
                count++;
            }
        }
        this.giveDamage(shooter,count);
        markedBy.removeIf(player -> player == shooter);
    }

    /**
     * this method return the damage track
     * @return plance's damage track
     */
    public List<String> getDamageTrack() {
        return damageTrack;
    }

    /**
     * this method set how many times the player was killed
     * @param i how many times the player was killed
     */
    public void setKilled(int i) {
        killed[i] = true;
    }
}