package it.polimi.sw2019.model;

import it.polimi.sw2019.model.weapon_power.Power;
/**Class Alternative: describes the alternative effect of the weapon that is used instead of the basic one
 * @author Merita Mullameti
 */
public class Alternative extends Weapon{

    //the effect of the weapon that is used instead of the basic one
    private Power alternativePower;
    //the ammo that is needed to pay to be able to use this effect
    private String extraCost;
    //a description of the effect the weapon can do in alternative to the basic effect
    public String descriptionAlternativePower;
    /**Constructor of the class
     * @param alternativePower
     * @param extraCost
     * @param descriptionAlternativePower
     */
    public Alternative(Power alternativePower , String extraCost , String descriptionAlternativePower){
        this.alternativePower=alternativePower;
        this.extraCost=extraCost;
        this.descriptionAlternativePower=descriptionAlternativePower;
    }
    /**
     * @return the extra cost to pay for the player to use the alternative
     */
    public String getExtraCost(){
        return extraCost;
    }

    public void attackAlternative(){

    }
    /**
     * @return a description of the effect the weapon can do in alternative to the basic effect
     */
    public String getDescriptionAlternativePower(){
        return descriptionAlternativePower;
    }
}
