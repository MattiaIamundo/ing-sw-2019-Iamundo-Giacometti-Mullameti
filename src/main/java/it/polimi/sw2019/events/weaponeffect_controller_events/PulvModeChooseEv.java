package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Pulverize mode, the alternative effect of Sledgehammer
 */
public class PulvModeChooseEv implements WeaponEvent{
    private String attacker;
    private ArrayList<String> targets;
    private ArrayList<String> movements;

    /**
     * @param attacker is the player that invoke the effect
     * @param targets is the list of the players that can be selected as a valid target
     * @param movements is the list of the square where the target can be moved, they are expressed by their cardinal direction e.g. north, north-north
     */
    public PulvModeChooseEv(String attacker, ArrayList<String> targets, ArrayList<String> movements) {
        this.attacker = attacker;
        this.targets = targets;
        this.movements = movements;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public ArrayList<String> getTargets() {
        return targets;
    }

    public ArrayList<String> getMovements() {
        return movements;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
