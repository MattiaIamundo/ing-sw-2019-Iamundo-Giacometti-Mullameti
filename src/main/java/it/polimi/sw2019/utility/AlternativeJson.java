package it.polimi.sw2019.utility;

public class AlternativeJson {

    private String name;
    //at the top it cost 3 ammo to recharge [3]
    private String[] rechargeCost;
    //0 weapon not load
    //1 weapon load
    private boolean isLoad;
    private String power;
    private String descriptionPower;
    private String alternativePower;
    private String[] extraCost;
    private String descriptionAlternativePower;

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

    public String getAlternativePower() {
        return alternativePower;
    }

    public String[] getExtraCost() {
        return extraCost;
    }

    public String getDescriptionAlternativePower() {
        return descriptionAlternativePower;
    }
}
