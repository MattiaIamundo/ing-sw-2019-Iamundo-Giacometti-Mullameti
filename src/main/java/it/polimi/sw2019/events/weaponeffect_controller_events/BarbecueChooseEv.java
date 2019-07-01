package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * This class represent the choose event for Barbecue mode, the alternative effect of Flamethrower.
 */
public class BarbecueChooseEv implements WeaponEvent{
    private String attacker;
    private ArrayList<String> directions;

    /**
     * @param attacker is the player that invoke the effect
     * @param directions is the list of the directions that can be chose. Listed as cardinal direction e.g. north, west
     */
    public BarbecueChooseEv(String attacker, ArrayList<String> directions) {
        this.attacker = attacker;
        this.directions = directions;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }

    public ArrayList<String> getDirections() {
        return directions;
    }
}
