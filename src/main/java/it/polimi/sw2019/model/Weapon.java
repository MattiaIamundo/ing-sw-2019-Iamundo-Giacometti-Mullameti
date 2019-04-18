package it.polimi.sw2019.model;

import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.WeaponView;


/**
 * this class stands for the game's weapons
 * @author Luca Giacometti
 */
public abstract class Weapon extends WeaponView implements Cloneable {

    private String name;
    private String[] rechargeCost = new String[3];
    //0 weapon not load
    //1 weapon load
    private boolean isLoad;
    private Power power;
    private String descriptionPower;

    /**
     * this is the constructor
     * @param name weapon's name
     * @param power weapon's power
     * @param descriptionPower weapon's description
     */
    public Weapon (String name, Power power, String descriptionPower){
        this.name = name;
        this.power = power;
        this.descriptionPower = descriptionPower;
        this.isLoad = true;
    }

    /**
     *
     * @return weapon's recharge cost
     */
    public String[] getCost(){
        return rechargeCost;
    }

    /**
     *
     * @param attacker the player who wants to attack
     */
    public void attack (Player attacker){

        power.usePower(attacker);
    }

    /**
     *
     * @return weapon's description
     */
    public String getDescriptionPower(){
        return descriptionPower;
    }

    /**
     *
     * @return true if the weapon is ready to fire
     */
    public boolean getIsLoad(){
        return isLoad;
    }
}
