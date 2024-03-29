package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * These class represent the choose event of Flamethrower, the basic effect of Flamethrower
 */
public class FlamethrowerChooseEv implements WeaponEvent{
    private String attacker;
    private HashMap<String, ArrayList<String>> firstline;
    private HashMap<String, ArrayList<String>> secondline;

    /**
     * @param attacker is the player that invoke the effect
     * @param firstline is an hash map that contains the valid players that are 1 squares away from the attacker. The key is the cardinal direction of the target square, the corresponding value
     *                  is the list of the players that are on the square on the direction expressed by the key. The list can be empty.
     * @param secondline is an hash map that contains the valid players that are 2 squares away from the attacker. The key is the cardinal direction of the target square, the corresponding value
     *                   is the list of the players that are on the square on the direction expressed by the key. The list can be empty.
     */
    public FlamethrowerChooseEv(String attacker, HashMap<String, ArrayList<String>> firstline, HashMap<String, ArrayList<String>> secondline) {
        this.attacker = attacker;
        this.firstline = firstline;
        this.secondline = secondline;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public HashMap<String, ArrayList<String>> getFirstline() {
        return firstline;
    }

    public HashMap<String, ArrayList<String>> getSecondline() {
        return secondline;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
