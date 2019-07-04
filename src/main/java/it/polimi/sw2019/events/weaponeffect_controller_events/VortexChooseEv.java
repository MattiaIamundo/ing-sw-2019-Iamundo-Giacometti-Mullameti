package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * These class represent the choose event of Vortex cannon, the basic effect of Vortex Cannon
 */
public class VortexChooseEv implements WeaponEvent{
    private String attacker;
    private HashMap<String, ArrayList<String>> validchoices;

    /**
     * @param attacker is the player that invoke the effect
     * @param validchoices the hash map use as key the absolute coordinates of the squares on which the Vortex can be positioned
     *                     the key's value is the list of the players that can be selected as a valid target
     */
    public VortexChooseEv(String attacker, HashMap<String, ArrayList<String>> validchoices) {
        this.attacker = attacker;
        this.validchoices = validchoices;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public HashMap<String, ArrayList<String>> getValidchoices() {
        return validchoices;
    }


    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
