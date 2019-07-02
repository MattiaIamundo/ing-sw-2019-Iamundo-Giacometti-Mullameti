package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.Map;

/**
 * These class represent the choose event of Rocket launcher, the basic effect of Rocket Launcher
 */
public class RocketLaunchChooseEv implements WeaponEvent{
    private String attacker;
    private Map<String, ArrayList<String>> targets;

    /**
     * @param attacker is the player that invoke the effect
     * @param targets the hash map use as key the nickname of the players that can be selected as a valid target, and as value the list of the squares in which the key's player can be moved
     *                in, the squares are expressed by their cardinal direction e.g. north, south
     */
    public RocketLaunchChooseEv(String attacker, Map<String, ArrayList<String>> targets) {
        this.attacker = attacker;
        this.targets = targets;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public Map<String, ArrayList<String>> getTargets() {
        return targets;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
