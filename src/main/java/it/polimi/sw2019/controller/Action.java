package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.Player;

public interface Action {
     //modified for sonarreport
     static void useAction(Player player){
          throw new IllegalAccessError();
     }
}
