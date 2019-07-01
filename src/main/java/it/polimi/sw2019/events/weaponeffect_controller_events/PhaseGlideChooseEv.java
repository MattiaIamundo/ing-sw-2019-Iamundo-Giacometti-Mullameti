package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Phase glide, the first optional effect of Plasma Gun
 */
public class PhaseGlideChooseEv implements WeaponEvent{
    private String attacker;
    private ArrayList<String> positions;

    /**
     * @param attacker is the player that invoke the effect
     * @param positions is the list of the valid squares in which the attacker can be moved, they are expressed by their cardinal direction e.g. north, north-north, south-east
     */
    public PhaseGlideChooseEv(String attacker, ArrayList<String> positions) {
        this.attacker = attacker;
        this.positions = positions;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname() {
        return attacker;
    }

    public ArrayList<String> getPositions() {
        return positions;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
