package it.polimi.sw2019.model;

import it.polimi.sw2019.events.client_event.MVevent.NotifyReloadEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.PowerChooseEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * this class stands for the game's weapons
 * @author Luca Giacometti
 */
public abstract class Weapon extends Observable<PowerChooseEv> implements Cloneable, Serializable {

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
    public void setIsLoad(String nickname, boolean set) {
        this.isLoad = set;
        //notify(new NotifyReloadEv(player.getNickname(), "reload"));
    }
    /**
     *
     * @return the weapon's power
     */
    public Power getPower() {
        return power;
    }

    public void choosePower(Boolean itsAlternative, HashMap<String, ArrayList<String>> powers, HashMap<String, Integer> availableAmmo, HashMap<String, String> availablePowerUps){
        notify(new PowerChooseEv(itsAlternative, powers, availableAmmo, availablePowerUps));
    }
}
