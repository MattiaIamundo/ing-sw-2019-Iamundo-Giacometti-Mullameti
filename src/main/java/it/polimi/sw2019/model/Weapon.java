package it.polimi.sw2019.model;

import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.WeaponView;

public abstract class Weapon extends WeaponView implements Cloneable {

    private String name;
    private String[] rechargeCost = new String[3];
    private Power power;
    private String descriptionPower;

    public String[] getCost(){

    }
    public void attack(){

    }
    public String getDescriptionPower(){

    }
}
