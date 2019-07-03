package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * These class represent the choose event of Tractor beam, the basic effect of Tractor Beam
 */
public class TractorBeamChooseEv implements WeaponEvent {
    private String attacker;
    private HashMap<String, ArrayList<String>> valid;

    /**
     * @param attacker is the player that invoke the effect
     * @param valid the hash map use as keys the players that can be selected as a valid target and the corresponding value is the list of the positions in which that player can be moved in,
     *              the positions are expressed by their cardinal direction (e.g. north, north-north, south-east) relative to the actual player position or zero for no movement
     */
    public TractorBeamChooseEv(String attacker, HashMap<String, ArrayList<String>> valid) {
        this.attacker = attacker;
        this.valid = valid;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public HashMap<String, ArrayList<String>> getValid() {
        return valid;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
