package it.polimi.sw2019.view;

import it.polimi.sw2019.model.Weapon;

public class WeaponView extends Observable implements Observer{

    private Weapon weapon;

    protected void getWeaponName(){}
    protected void getWeaponDescription(){}
    protected void getWeaponEffect(){}
    protected void getEffectCost(){}
}
