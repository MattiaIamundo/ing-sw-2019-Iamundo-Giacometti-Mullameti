package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Weapon;
//modified for sonarreport
public abstract class MoveReShoot implements Action{

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
