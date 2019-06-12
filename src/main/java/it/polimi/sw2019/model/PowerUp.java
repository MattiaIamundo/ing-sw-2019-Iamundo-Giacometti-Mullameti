package it.polimi.sw2019.model;

import it.polimi.sw2019.view.PowerUpView;

import java.io.Serializable;

/**
 * this class represents the power up cards
 * @author Luca Giacometti
 */
public class PowerUp extends PowerUpView implements Cloneable, Serializable {

    private String color;
    private String name;
    private EffectBehaviour effect;

    /**
     * this is the constructor
     * @param color the power up's color
     * @param name the power up's name
     * @param effect the power up's effect
     */
    public PowerUp (String color, String name, EffectBehaviour effect) {
        this.color = color;
        this.name = name;
        this.effect = effect;
    }

    /**
     * this method has to show to the view the power up
     */
    @Override
    protected void showPowerUp() {

    }

    /**
     * @return the power up's color
     */
    public String getColor(){
        return color;
    }
    /**
     * @return the power up's name
     */
    public String getName(){
        return name;
    }

    public void setEffect(EffectBehaviour eff) {
        this.effect = eff;
    }

    public String getEffectToString() {
        return this.effect.toString();
    }
}
