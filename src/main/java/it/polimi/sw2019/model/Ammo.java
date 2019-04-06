package it.polimi.sw2019.model;

import it.polimi.sw2019.view.AmmoView;

public abstract class Ammo extends AmmoView implements Cloneable {

    private String colorFirst;
    private String colorSecond;

    public void ammo (String clrFrst, String clrScnd){

    }
    public String getColorFirst(){
        return colorFirst;
    }
    public String getColorSecond(){
        return colorSecond;
    }
}
