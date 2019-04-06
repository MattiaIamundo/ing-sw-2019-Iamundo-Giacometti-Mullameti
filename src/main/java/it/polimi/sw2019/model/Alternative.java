package it.polimi.sw2019.model;

import it.polimi.sw2019.model.weapon_power.Power;

public class Alternative extends Weapon{

    private Power alternativePower;
    private String extraCost;
    public String descriptionAlternativePower;

    public String getExtraCost(){
        return extraCost;
    }
    public void attackAlternative(){

    }
    public String getDescriptionAlternativePower(){
        return descriptionAlternativePower;
    }
}
