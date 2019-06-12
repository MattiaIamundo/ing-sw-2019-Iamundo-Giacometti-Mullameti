package it.polimi.sw2019.model;

import it.polimi.sw2019.model.weapon_power.Power;


/**Class Additive : describes the effects some weapons  can do in addition to the basic effect.
 * @author Merita Mullameti
 */
public class Additive extends Weapon{

    //the effect the weapon can do in addition to the basic effect
    private Power additivePower;
    //the ammo that is needed to pay to be able to use this effect
    private String additiveCost;
    //describes the effect the weapon can do in addition to the basic effect
    private String descriptionAdditivePower;

    /**Constructor of the class
     * @param additivePower the second power which can be used with the main one
     * @param additiveCost the additive power's cost
     * @param descriptionAdditivePower the additive power's description
     */
    public Additive (String name, Power power, String descriptionPower,
                     Power additivePower , String additiveCost , String descriptionAdditivePower, String[] rc ){

        super(name, power, descriptionPower, rc);
        this.additivePower = additivePower;
        this.additiveCost = additiveCost;
        this.descriptionAdditivePower = descriptionAdditivePower;
    }
    /**
     * @return the extra cost needed to activate this part of the weapon
     */
    public String getAdditiveCost(){
        return additiveCost;
    }

    /**
     * @param attacker the player who wants to attack
     */
    public void attackAdditive (Player attacker){

        additivePower.usePower(attacker);
    }

    /**
     * @return the weapon's additive power
     */
    public Power getAdditivePower() {
        return additivePower;
    }
    /**
     * @return a description of the effect the weapon can do in addition to the basic effect
     */
    public String getDescriptionAdditivePower(){
        return descriptionAdditivePower;
    }
}
