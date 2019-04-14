package it.polimi.sw2019.model;

import it.polimi.sw2019.view.PowerUpView;
/**
 * this class represents the power up cards
 */
public class PowerUp extends PowerUpView implements Cloneable {

    private String color;
    private String name;
    private EffectBehaviour effect;

    /**
     * @author Luca Giacometti
     * @param color
     * @param name
     * @param effect
     * this is the constructor
     */
    public PowerUp (String color, String name, EffectBehaviour effect) {
        this.color = color;
        this.name = name;
        this.effect = effect;
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
    /**
     * @param target it is the player which will be hit
     */
    public void useEffect(Player target){

    }
}
