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
    private String descriptionAlternativePower;

    /**Constructor of the class
     * @param alternativePower the second power which can be used instead of the main one
     * @param extraCost the alternative power's cost
     * @param descriptionAlternativePower the alternative power's description
     */
    public Alternative(String name, Power power, String descriptionPower,
                       Power alternativePower , String extraCost , String descriptionAlternativePower){

        super(name, power, descriptionPower);
        this.alternativePower = alternativePower;
        this.extraCost = extraCost;
        this.descriptionAlternativePower = descriptionAlternativePower;
    }
    /**
     * @return the extra cost to pay for the player to use the alternative
     */
    public String getExtraCost(){
        return extraCost;
    }

    /**
     *
     * @param attacker the player who declared the attack
     */
    public void attackAlternative(Player attacker){

        alternativePower.usePower(attacker);
    }

    /**
     * @return a description of the effect the weapon can do in alternative to the basic effect
     */
    public String getDescriptionAlternativePower(){
        return descriptionAlternativePower;
    }
}
