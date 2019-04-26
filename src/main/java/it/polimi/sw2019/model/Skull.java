package it.polimi.sw2019.model;

/**
 * this class symbolizes the skulls which are present on the table
 * @author Luca Giacometti
 */
public class Skull {

    private boolean isPresent;
    private boolean isOverkilled;
    //the player who killed an opponent
    private Player killer;

    /**
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
     * @return the skull's attribute isPresent
     */
    public boolean getIsPresent (){
        return this.isPresent;
    }

    /**
     * @return the skull's attribute isOverkilled
     */
    public boolean getIsOverkilled (){
        return this.isOverkilled;
    }
    /**
     * it sets the double mark
     */
    public void setIsOverkilled(){
        this.isOverkilled = true;
    }
    /**
     * @param killer the player which killed another player
     */
    public void setKiller(Player killer) {
        this.killer = killer;
    }
}