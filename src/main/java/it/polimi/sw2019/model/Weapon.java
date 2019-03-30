package it.polimi.sw2019.model;

import it.polimi.sw2019.model.weapon_power.Power;

public abstract class Weapon {

    private String name;
    private String[] rechargeCost = new String[3];
    private Power power;
    private String descriptionPower;

    public String[] getCost(){

    }
    public void attack(Player target){

    }
    public String getDescriptionPower(){

    }
}
