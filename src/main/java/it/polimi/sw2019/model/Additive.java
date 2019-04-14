package it.polimi.sw2019.model;

import it.polimi.sw2019.model.weapon_power.Power;
/**Class Additive : describes the effects some weapons  can do in addition to the basic effect.
 * @author Merita Mullameti
 */
public class Additive extends  Weapon{

    //the effect the weapon can do in addition to the basic effect
    private Power additivePower;
    //the ammo that is needed to pay to be able to use this effect
    private String additiveCost;
    //decribes the effect the weapon can do in addition to the basic effect
    private String descriptionAdditivePower;

    /**Constructor of the class
     * @param additivePower
     * @param additiveCost
     * @param descriptionAdditivePower
     */
    public Additive(Power additivePower , String additiveCost , String descriptionAdditivePower ){
        this.additivePower=additivePower;
        this.additiveCost=additiveCost;
        this.descriptionAdditivePower=descriptionAdditivePower;
    }
    /**
     * @return the extra cost needed to activate this part of the weapon
     */
    public String getExtraCost(){
        return additiveCost;
    }

    public void attackAdditive (){

    }
    /**
     * @return a description of the effect the weapon can do in addition to the basic effect
     */
    public String getDescriptionAdditivePower(){
        return descriptionAdditivePower;
    }
}
