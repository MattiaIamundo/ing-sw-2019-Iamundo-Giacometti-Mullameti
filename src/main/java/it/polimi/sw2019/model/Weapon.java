package it.polimi.sw2019.model;

import it.polimi.sw2019.exception.InvalidPlayerException;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.WeaponView;

import java.util.Scanner;

/**
 * this class stands for the game's weapons
 * @author Luca Giacometti
 */
public abstract class Weapon extends WeaponView implements Cloneable {

    private String name;
    private String[] rechargeCost = new String[3];
    //0 weapon not load
    //1 weapon load
    private boolean isLoad = true;
    private Power power;
    private String descriptionPower;
    private Scanner scanner = new Scanner(System.in);

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
     * @param player the target
     * @throws InvalidPlayerException if the target is incorrect
     */
    public void findPlayer(String player) throws InvalidPlayerException {
        boolean out = false;
        int i = 0;
        // finding if the name is present in the list of players
        for ( i = 0; i < 5 || out ; i++){

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
