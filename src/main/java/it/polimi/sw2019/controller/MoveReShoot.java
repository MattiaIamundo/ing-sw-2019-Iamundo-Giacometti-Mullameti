package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Weapon;

public class MoveReShoot implements Action{

    private Space moveto;
    private Weapon weapon;

    public void useAction(){

    }

    private void reload(){

    }

    private boolean isBeforeFirstPlayer(){
        return true;
    }
}
