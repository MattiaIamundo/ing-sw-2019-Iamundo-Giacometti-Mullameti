package it.polimi.sw2019.model;
/**
 * this class represents the space where a player can take ammo
 * @author Luca Giacometti
 */
public class SpaceAmmo extends Space {

    private Ammo ammo;

    /**
     * this is the constructor
     * @param north the north connection
     * @param east the east connection
     * @param south the south connection
     * @param west the west connection
     * @param room the space's room
     * @param ammo the space's ammo in this moment
     */
    public SpaceAmmo(Connection north, Connection east, Connection south, Connection west, String room, Ammo ammo) {
        super(north, east, south, west, room);
        this.ammo = ammo;
    }
    /**
     * @return tha ammo card
     */
    public Ammo takeAmmo(){
        return ammo;
    }

    /**
     * this method sets an ammo
     * @param ammo the ammo to be set
     */
    public void setAmmo( Ammo ammo){
        //there is not any ammo
        if( this.ammo != null){

            this.ammo = ammo;
        }
        else {
            System.out.println("There is already an ammo in this ammo position!\n");
        }
    }
}
