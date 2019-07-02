package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Cozy fire mode, the alternative effect of Furnace
 */
public class CozyFireModeChooseEv implements WeaponEvent{
    private String attacker;
    private ArrayList<String> directions;

    /**
     * @param attacker is the player that invoke the effect
     * @param directions is the list of the squares that can be selected as target, it's represented by their cardinal direction e.g. north, west
     */
    public CozyFireModeChooseEv(String attacker, ArrayList<String> directions) {
        this.attacker = attacker;
        this.directions = directions;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
