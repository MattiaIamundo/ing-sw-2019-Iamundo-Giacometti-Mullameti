package it.polimi.sw2019.model;
/**
 * this class represents the space where a player can take ammo
 */
public class SpaceAmmo extends Space {

    private Ammo ammo;

    /**
     * @author Luca Giacometti
     * @param nord
     * @param est
     * @param sud
     * @param ovest
     * @param room
     * @param ammo
     * this is the constructor
     */
    public SpaceAmmo(Connection nord, Connection est, Connection sud, Connection ovest, String room, Ammo ammo) {
        super(nord, est, sud, ovest, room);
        this.ammo = ammo;
    }
    /**
     * @return tha ammo card
     */
    public Ammo takeAmmo(){
        return ammo;
    }
}
