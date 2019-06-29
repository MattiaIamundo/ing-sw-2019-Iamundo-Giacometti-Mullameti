package it.polimi.sw2019.controller;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.view.ObservableByGame;

/**Class Grab: One of player`s basic action
 * @author Merita Mullameti
 */
public class Grab extends ObservableByGame implements Action {

    private Ammo ammo;
    /**
     * @param player the player  who is going to be attacked using this power up card
     */
    public void useAction(Player player) {

        if(player.getPosition() instanceof SpaceAmmo){
            //Ammo ammoCard = SpaceAmmo.takeAmmo();

            //player.addAmmo(ammoCard);
        }else{
            System.out.println("There is no ammo in this space !");
        }

    }
}

