package it.polimi.sw2019.model;

import it.polimi.sw2019.model.weapon_power.Power;

/**
 * this class represents the weapon cards which have two power to be used
 * @author Luca Giacometti
 */
public class DoubleAdditive extends Weapon{

    private Power firstAdditivePower;
    private String firstExtraCost;
    private Power secondAdditivePower;
    private String secondExtraCost;
    private String descriptionFirstAdditivePower;
    private String descriptionSecondAdditivePower;

    /**
     * this is the constructor
     * @param firstAdditivePower first optional attack
     * @param firstExtraCost the first attack's cost
     * @param secondAdditivePower second optional attack
     * @param secondExtraCost the second attack's cost
     * @param descriptionFirstAdditivePower the first's optional attack description
     * @param descriptionSecondAdditivePower the second's optional attack description
     */
    public DoubleAdditive (String name, Power power, String descriptionPower,
                            Power firstAdditivePower, String firstExtraCost, Power secondAdditivePower,
                           String secondExtraCost, String descriptionFirstAdditivePower,
                           String descriptionSecondAdditivePower, String[] rc ){

        super(name, power, descriptionPower, rc);
        this.descriptionFirstAdditivePower = descriptionFirstAdditivePower;
        this.descriptionSecondAdditivePower = descriptionSecondAdditivePower;
        this.firstAdditivePower = firstAdditivePower;
        this.firstExtraCost = firstExtraCost;
        this.secondAdditivePower = secondAdditivePower;
        this.secondExtraCost = secondExtraCost;
    }

    /**
     * This method returns the first additive power's cost
     * @return the first additive power's cost
     */
    public String getFirstExtraCost(){
        return firstExtraCost;
    }

    /**
     * This method returns the second additive power's cost
     * @return the second additive power's cost
     */
    public String getSecondExtraCost(){
        return secondExtraCost;
    }

    /**
     * This method  implements the use of the first power by the attacker
     * @param attacker the player who calls the attack
     */
    public void attackFirstAdditive(Player attacker){

        firstAdditivePower.usePower(attacker);
    }

    /**
     * This method  implements the use of the second power by the attacker
     * @param attacker the player who calls the attack
     */
    public void attackSecondAdditive(Player attacker){

        secondAdditivePower.usePower(attacker);
    }

    /**
     * This method returns the first additive power's description
     * @return the first additive power's description
     */
    public String getDescriptionFirstAdditivePower(){
        return descriptionFirstAdditivePower;
    }

    /**
     * This method returns the second additive power's description
     * @return the second additive power's description
     */
    public String getDescriptionSecondAdditivePower(){
        return descriptionSecondAdditivePower;
    }

    /**
     * This method returns the weapon's first additive power
     * @return the weapon's first additive power
     */
    public Power getFirstAdditivePower() {
        return firstAdditivePower;
    }

    /**
     * This method returns the weapon's second additive power
     * @return the weapon's second additive power
     */
    public Power getSecondAdditivePower() {
        return secondAdditivePower;
    }
}
