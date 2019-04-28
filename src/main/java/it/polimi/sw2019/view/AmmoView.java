package it.polimi.sw2019.view;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.model.Ammo;

public abstract class AmmoView extends Observable <Game> implements Observer <Ammo>{

    //private Ammo ammo;

    protected abstract void showAmmo();

    public void update(Ammo message) {
        showAmmo();
    }
}
