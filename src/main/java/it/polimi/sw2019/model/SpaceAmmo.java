package it.polimi.sw2019.model;
/**
 * this class represents the space where a player can take ammo
 * @author Luca Giacometti
 */
public class SpaceAmmo extends Space {

    private Ammo ammo = null;

    /**
     * this is the constructor
     * @param north the north connection
     * @param east the east connection
     * @param south the south connection
     * @param west the west connection
     * @param room the space's room
     */
    public SpaceAmmo(Connection north, Connection east, Connection south, Connection west, String room) {
        super(north, east, south, west, room);
    }
    /**
     * @return tha ammo card
     */
    public Ammo takeAmmo(){

        return ammo;
    }

    /**
     * this method sets an ammo
     * @param ammos the ammo to be set
     */
    public void setAmmo( Ammo ammos){
        //there is not any ammo
        if( ammo != null){

            ammo = ammos;
        }
        else {
            System.out.println("There is already an ammo in this ammo position!\n");
        }
    }
}
