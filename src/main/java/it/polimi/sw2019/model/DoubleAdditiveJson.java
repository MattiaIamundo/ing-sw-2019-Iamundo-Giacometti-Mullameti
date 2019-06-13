package it.polimi.sw2019.model;

public class DoubleAdditiveJson {

    private String name;
    //at the top it cost 3 ammo to recharge [3]
    private String[] rechargeCost;
    //0 weapon not load
    //1 weapon load
    private boolean isLoad;
    private String power;
    private String descriptionPower;
    private String firstAdditivePower;
    private String firstExtraCost;
    private String secondAdditivePower;
    private String secondExtraCost;
    private String descriptionFirstAdditivePower;
    private String descriptionSecondAdditivePower;

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

    public String getFirstAdditivePower() {
        return firstAdditivePower;
    }

    public String getFirstExtraCost() {
        return firstExtraCost;
    }

    public String getSecondAdditivePower() {
        return secondAdditivePower;
    }

    public String getSecondExtraCost() {
        return secondExtraCost;
    }

    public String getDescriptionFirstAdditivePower() {
        return descriptionFirstAdditivePower;
    }

    public String getDescriptionSecondAdditivePower() {
        return descriptionSecondAdditivePower;
    }
}
