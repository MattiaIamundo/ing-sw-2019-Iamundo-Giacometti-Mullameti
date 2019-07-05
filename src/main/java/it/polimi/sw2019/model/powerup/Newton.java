package it.polimi.sw2019.model.powerup;

import it.polimi.sw2019.events.powerup_events.NewtonChooseEv;
import it.polimi.sw2019.exception.IllegalDirectionException;
import it.polimi.sw2019.exception.InvalidDirectionException;
import it.polimi.sw2019.exception.InvalidInputException;
import it.polimi.sw2019.model.EffectBehaviour;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.view.Observable;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
/**Class Newton: the power up card described in this class gives you the possibility to move your enemy from one space to another
 * @author Merita Mullameti
 */

public class Newton implements EffectBehaviour, Serializable {
    private Space moveto;
    /**
     * @param target the player  who is going to be attacked using this power up card
     */
    @Override
    public  void useEffect (Player target){
        target.setPosition(moveto);
    }

    public Space getMoveto() {
        return moveto;
    }

    public void setMoveto(Space moveto) {
        this.moveto = moveto;
    }
}
