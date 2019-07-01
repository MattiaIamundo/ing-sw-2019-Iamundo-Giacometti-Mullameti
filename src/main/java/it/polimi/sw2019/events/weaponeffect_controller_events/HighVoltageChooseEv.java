package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.model.Player;

import java.util.ArrayList;

/**
 * These class represent the choose event of High voltage, the second optional effect of T.H.O.R.
 */
public class HighVoltageChooseEv implements WeaponEvent{
    protected String attacker;
    protected ArrayList<String> valid;
    protected ArrayList<String> notselectable;
    protected ArrayList<String> notreachable;

    /**
     * @param attacker is the player that invoke the effect
     * @param valid is the list of the players that can be selected as a valid target
     * @param notselectable is the the list containing in first position the attacker, and then the first and the second target of the weapon. They can't be selected as a target
     * @param notreachable is the list of the players that can't be selected as a target due to be out of the range of the effect
     */
    public HighVoltageChooseEv(String attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable) {
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
