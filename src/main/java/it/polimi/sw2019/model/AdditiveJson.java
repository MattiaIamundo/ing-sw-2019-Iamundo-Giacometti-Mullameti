package it.polimi.sw2019.model;

public class AdditiveJson {

    private String name;
    //at the top it cost 3 ammo to recharge [3]
    private String[] rechargeCost;
    //0 weapon not load
    //1 weapon load
    private boolean isLoad;
    private String power;
    private String descriptionPower;
    //the effect the weapon can do in addition to the basic effect
    private String additivePower;
    //the ammo that is needed to pay to be able to use this effect
    private String additiveCost;
    //describes the effect the weapon can do in addition to the basic effect
    private String descriptionAdditivePower;

    public String getName() {
        return name;
    }

    public String[] getRechargeCost() {
        return rechargeCost;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public String getPower() {
        return power;
    }

    public String getDescriptionPower() {
        return descriptionPower;
    }

    public String getAdditivePower() {
        return additivePower;
    }

    public String getAdditiveCost() {
        return additiveCost;
    }

    public String getDescriptionAdditivePower() {
        return descriptionAdditivePower;
    }
}
