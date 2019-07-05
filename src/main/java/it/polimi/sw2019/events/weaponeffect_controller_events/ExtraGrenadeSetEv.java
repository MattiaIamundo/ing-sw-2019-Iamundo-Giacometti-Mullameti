package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Extra grenade, the optional effect of Grenade Launcher
 */
public class ExtraGrenadeSetEv extends WeaponEffectSetEv {
    private String square;
    private String moveto;

    /**
     * These is the constructor in case the target of the basic effect of the weapon it's already moved
     * @param square is the absolute coordinates of the square selected as the target, must be in the validsquare list of the choose event
     */
    public ExtraGrenadeSetEv(String square) {
        this.square = square;
        moveto = null;
    }

    /**
     * This is the constructor in case the target of the basic effect was already moved
     * @param square is the absolute coordinates of the square selected as the target, must be in the validsquare list of the choose event
     * @param moveto is the direction in which the attacker want to move the previous target, must be in the moveto list of the choose event
     */
    public ExtraGrenadeSetEv(String square, String moveto) {
        this.square = square;
        this.moveto = moveto;
    }

    public String getSquare() {
        return square;
    }

    public String getMoveto() {
        return moveto;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
