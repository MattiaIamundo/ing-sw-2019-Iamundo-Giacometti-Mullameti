package it.polimi.sw2019.model;

import it.polimi.sw2019.model.weapon_power.Power;

import java.io.Serializable;


/**
 * this class stands for the game's weapons
 * @author Luca Giacometti
 */
public abstract class Weapon implements Cloneable, Serializable {

    private String name;
    //at the top it cost 3 ammo to recharge [3]
    private String[] rechargeCost;
    //true weapon load, false not load
    private boolean isLoad;
    private Power power;
    private String descriptionPower;

    /**
     * this is the constructor
     * @param name weapon's name
     * @param power weapon's power
     * @param descriptionPower weapon's description
     */
    public Weapon (String name, Power power, String descriptionPower,String[] rechargeCost ){
        this.name = name;
        this.rechargeCost = rechargeCost;
        this.power = power;
        this.descriptionPower = descriptionPower;
        this.isLoad = true;
    }

    /**
     * weapon's name getter
     * @return the weapon's name
     */
    public String getName(){
        return name;
    }

    /**
     * recharge'cost getter
     * @return weapon's recharge cost
     */
    public String[] getCost(){
        return rechargeCost;
    }

    /**
     * weapon's description getter
     * @return weapon's description
     */
    public String getDescriptionPower(){
        return descriptionPower;
    }

    /**
     * it show if the weapon is load
     * @return true if the weapon is ready to fire
     */
    public boolean getIsLoad(){
        return isLoad;
    }

    /**
     * set the new weapon's condition
     * @param nickname the player's nickname
     * @param set this variable is true if the weapon is load and false if it is discharged
     */
    public void setIsLoad(String nickname, boolean set) {
        this.isLoad = set;
        //notify(new NotifyReloadEv(player.getNickname(), "reload"));
    }
    /**
     * weapon's power getter
     * @return the weapon's power
     */
    public Power getPower() {
        return power;
    }

}
