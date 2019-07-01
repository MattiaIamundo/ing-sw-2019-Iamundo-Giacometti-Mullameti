package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Shotgun, the basic effect of Shotgun
 */
public class ShotgunChooseEv implements WeaponEvent{
    private String attacker;
    private ArrayList<String> valid;
    private ArrayList<String> notreachable;
    private ArrayList<String> moveto;

    /**
     * @param attacker is the player that invoke the effect
     * @param valid is the list of the players that can be selected as a valid target
     * @param notreachable is the list of the players that can't be selected as target due to be out of the effect's range
     * @param moveto is the list of the valid direction in which the target can be moved in, the direction are expressed by their cardinal direction e.g. north, south
     */
    public ShotgunChooseEv(String attacker, ArrayList<String> valid, ArrayList<String> notreachable, ArrayList<String> moveto) {
        this.attacker = attacker;
        this.valid = valid;
        this.notreachable = notreachable;
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

    public ArrayList<String> getValid() {
        return valid;
    }

    public ArrayList<String> getNotreachable() {
        return notreachable;
    }

    public ArrayList<String> getMoveto() {
        return moveto;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
