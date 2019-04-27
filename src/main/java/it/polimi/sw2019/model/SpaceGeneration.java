package it.polimi.sw2019.model;
/**
 * this class stands of the generation point
 * @author Luca Giacometti
 */
public class SpaceGeneration extends Space {

    private Weapon[] weapon = new Weapon[3];

    /**
     * this is the constructor
     * @param north the north connection
     * @param east the north connection
     * @param south the north connection
     * @param west the north connection
     * @param room the space's room
     */
    public SpaceGeneration (Connection north, Connection east, Connection south, Connection west, String room) {
        super(north, east, south, west, room);
    }

    public void setWeapon( Weapon weapon){
        //to go out the loop before
        boolean out = false;

        for (int i = 0; (i < 3) || out ; i++){
            //a weapon cans be inserted and i can go out the loop
            if ( this.weapon[i] == null ){

                this.weapon[i] = weapon;
                out = true;
            }

        }
        if ( !out ){

            System.out.println("This point of generation has already 3 weapons!\n");
        }
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