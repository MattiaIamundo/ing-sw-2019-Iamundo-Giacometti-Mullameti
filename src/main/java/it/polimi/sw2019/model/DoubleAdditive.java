package it.polimi.sw2019.model;

import it.polimi.sw2019.model.weapon_power.Power;

public class DoubleAdditive {

    private Power firstAdditivePower;
    private String firstExtraCost;
    private Power secondAdditivePower;
    private String secondExtraCost;
    private String descriptionFirstAdditivePower;
    private String descriptionSecondAdditivePower;

    public String getFirstExtraCost(){
        return firstExtraCost;
    }
    public String getSecondExtraCost(){
        return secondExtraCost;
    }
    public void attackFirstAdditive(){

    }
    public void attackSecondAdditive(){

    }
    public String getDescriptionFirstAdditivePower(){
        return descriptionFirstAdditivePower;
    }
    public String getDescriptionSecondAdditivePower(){
        return descriptionSecondAdditivePower;
    }
}
