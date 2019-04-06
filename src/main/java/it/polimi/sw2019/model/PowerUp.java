package it.polimi.sw2019.model;

import it.polimi.sw2019.view.PowerUpView;

public class PowerUp extends PowerUpView implements Cloneable {

    private String color;
    private String name;
    private EffectBehaviour effect;

    public String getColor(){
        return color;
    }
    public String getName(){
        return name;
    }
    public void useEffect(Player target){

    }
}
