package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Extra grenade, the optional effect of Grenade Launcher
 */
public class ExtraGrenadeChooseEv implements WeaponEvent{
    private String attacker;
    private ArrayList<String> validsquare;
    private ArrayList<String> moveto;

    /**
     * This is the constructor in case the target of the basic effect of the weapon it's already been moved
     * @param attacker is the player that invoke the effect
     * @param validsquare is the list of the squares that can be selected as a valid target area, they are represented by their absolute coordinates i.g. "x-y"
     */
    public ExtraGrenadeChooseEv(String attacker, ArrayList<String> validsquare) {
        this.attacker = attacker;
        this.validsquare = validsquare;
        moveto = null;
    }

    /**
     * This is the constructor in case the target of the basic effect wasn't moved
     * @param attacker is the player that invoke the effect
     * @param validsquare is the list of the squares that can be selected as a valid target area, they are represented by their absolute coordinates i.g. "x-y"
     * @param moveto is the list of the valid squares were the previous target can be moved on, they are represented by their cardinal direction e.g. north, west
     */
    public ExtraGrenadeChooseEv(String attacker, ArrayList<String> validsquare, ArrayList<String> moveto) {
        this.attacker = attacker;
        this.validsquare = validsquare;
        this.moveto = moveto;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public ArrayList<String> getValidsquare() {
        return validsquare;
    }

    public ArrayList<String> getMoveto() {
        return moveto;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
