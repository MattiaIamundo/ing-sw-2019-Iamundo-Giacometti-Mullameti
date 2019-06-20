package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Weapon;
//modified for sonarreport
public class MoveReShoot implements Action{

    private Space moveto;
    private Weapon weapon;

    public void useAction(Player player){

    }

    private void reload(){

    }

    private boolean isBeforeFirstPlayer(){
        return true;
    }
}
