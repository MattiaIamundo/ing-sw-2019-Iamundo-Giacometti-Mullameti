package it.polimi.sw2019.model;

import it.polimi.sw2019.exception.FullWeaponDeckException;

import java.util.ArrayList;
import java.util.List;

/**
 * this class stands of the generation point
 * @author Luca Giacometti
 */
public class SpaceGeneration extends Space {

    private ArrayList<Weapon> weapon = new ArrayList<>(3);

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

    public void setWeapon( Weapon weap) throws FullWeaponDeckException {

        if(this.weapon.isEmpty() || this.weapon.size() < 3) {
            this.weapon.add(weap);
        }
        else {
            throw new FullWeaponDeckException ();
        }

    }

    public Weapon getWeapon(int weaponNumber) {

        return this.weapon.remove(weaponNumber);
    }

    /**
     * @param weaponNum the number which represents a weapon
     * @return the weapon chosen
     */
    public Weapon takeWeapon(int weaponNum){
        return weapon.get(weaponNum);
    }
    /**
     * @return the list of 3 weapon which a player can choose
     */
    public List<Weapon> listWeapon(){
        return weapon;
    }
}