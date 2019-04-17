package it.polimi.sw2019.model;

import it.polimi.sw2019.controller.Action;

/**
 * this class implements the turn of the game
 * @author Luca Giacometti
 */
public class Turn {

    private static Player player;
    private Integer usedAction;
    private Action action;

    /**
     * this is the constructor
     * @param player who is playing
     * @param usedAction how many actions he used during this turn
     * @param action the action on air
     */
    public Turn (Player player, Integer usedAction, Action action){

        this.player = player;
        this.action = action;
        this.usedAction = usedAction;
    }

    /**
     *
     * @return the player who is playing this turn
     */
    public static Player getPlayer() {
        return player;
    }

    public void useAction(){

    }

    public void reload(Weapon weapon){

        if (weapon.getIsLoad() == true){
            System.out.println("The weapon is already load\n");
        }
        else{
            //String[3]
            //weapon.getCost()
            //Integer[3]
            //getPlayer().getAmmo()
        }
    }
}
