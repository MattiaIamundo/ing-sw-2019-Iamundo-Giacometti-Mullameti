package it.polimi.sw2019.model;

import it.polimi.sw2019.model.events.PowerChooseEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observable;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * this class stands for the game's weapons
 * @author Luca Giacometti
 */
public abstract class Weapon extends Observable<PowerChooseEv> implements Cloneable, Serializable {

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
     * this method has to show to the view the weapon
     */
    protected void showWeapon() {

    }

    /**
     *
     * @return the ammo's name
     */
    public String getName(){
        return name;
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
     * @param attacker the player who called the attack
     */
    public void attack(Player attacker){

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

    /**
     * @param set this variable is true if the weapon is load and false if it is discharged
     */
    public void setIsLoad(boolean set) {
        this.isLoad = set;
    }
    /**
     *
     * @return the weapon's power
     */
    public Power getPower() {
        return power;
    }

    public void choosePower(ArrayList<String> powers){
        notify(new PowerChooseEv(powers));
    }
}
