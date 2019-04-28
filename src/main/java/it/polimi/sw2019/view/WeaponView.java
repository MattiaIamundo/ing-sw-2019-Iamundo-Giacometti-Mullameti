package it.polimi.sw2019.view;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.model.Weapon;

public abstract class WeaponView extends Observable <Game> implements Observer <Weapon> {

    //private Weapon weapon;

    protected abstract void showWeapon();
    /**
     * this method show the update that one player did
     */
    public void update(Weapon message) {
        showWeapon();
    }
}
