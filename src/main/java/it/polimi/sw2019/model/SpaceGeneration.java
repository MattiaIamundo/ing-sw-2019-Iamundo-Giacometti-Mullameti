package it.polimi.sw2019.model;
/**
 * this class stands of the generation point
 */
public class SpaceGeneration extends Space {

    private Weapon[] weapon = new Weapon[3];

    /**
     * @author Luca Giacometti
     * @param nord
     * @param est
     * @param sud
     * @param ovest
     * @param room
     * this is the constructor
     */
    public SpaceGeneration (Connection nord, Connection est, Connection sud, Connection ovest, String room) {
        super(nord, est, sud, ovest, room);
    }
    /**
     * @param weaponNum the number which represents a weapon
     * @return the weapon chosen
     */
    public Weapon takeWeapon(Integer weaponNum){
        return weapon[weaponNum];
    }
    /**
     * @return the list of 3 weapon which a player can choose
     */
    public Weapon[] listWeapon(){
        return weapon;
    }
}