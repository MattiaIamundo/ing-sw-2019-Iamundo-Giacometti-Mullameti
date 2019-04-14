package it.polimi.sw2019.model;
/**
 * this class symbolizes the skulls which are present on the table
 */
public class Skull {

    public boolean isPresent;
    public boolean isOverkilled;
    //the player who killed an opponent
    private Player killer;

    /**
     * @author Luca Giacometti
     * this is the constructor
     * the skull is presents in the table,
     * so there are not a killer and an overkilled mark
     */
    public Skull (){
        this.isOverkilled = false;
        this.isPresent = true;
        this.killer = null;
    }
    /**
     * it can return null if the skull is present on the table
     * @return killer or null
     */
    public Player getKiller(){
        return killer;
    }
    /**
     * it removes the skull from the table
     */
    public void setIsPresent(){
        this.isPresent = false;
    }
    /**
     * it sets the double mark
     */
    public void setIsOverkilled(){
        this.isOverkilled = true;
    }
    /**
     * it sets who was the killer
     * @param killer
     */
    public void setKiller(Player killer){
        this.killer = killer;
    }
}