package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of Second lock, the optional effect of Lock Rifle
 */
public class SecondLockChooseEv implements WeaponEvent{
    private String attacker;
    private ArrayList<String> valid;
    private ArrayList<String> notselectable;
    private ArrayList<String> notreachable;

    /**
     * @param attacker is the player that invoke the effect
     * @param valid is the list of the players that can be selected as a valid target
     * @param notselectable is the list of the players that can't be selected as target due to be the attacker itself (in the first position of the array)
     *                      or the target of the basic effect
     * @param notreachable is the list of the players that can't be selected as target due to be out of the effect's range
     */
    public SecondLockChooseEv(String attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable) {
        this.attacker = attacker;
        this.valid = valid;
        this.notselectable = notselectable;
        this.notreachable = notreachable;
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

    public ArrayList<String> getNotselectable() {
        return notselectable;
    }

    public ArrayList<String> getNotreachable() {
        return notreachable;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
