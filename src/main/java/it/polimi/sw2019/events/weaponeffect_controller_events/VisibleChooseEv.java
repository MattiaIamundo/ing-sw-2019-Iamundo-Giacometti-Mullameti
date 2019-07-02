package it.polimi.sw2019.events.weaponeffect_controller_events;

import java.util.ArrayList;

public abstract class VisibleChooseEv implements WeaponEvent{
    protected String attacker;
    protected ArrayList<String> valid;
    protected ArrayList<String> notselectable;
    protected ArrayList<String> notreachable;

    /**
     * @param attacker is the player that invoke the effect
     * @param valid is the list of the players that can be selected as a valid target for the effect
     * @param notselectable is the list of the players that can't be selected as a target, the first one is the attacker, the other is the player selected as
     *                      target of the basic effect of the weapon
     * @param notreachable is the list of the players that can't be selected as a target due to be out of the range of the effect
     */
    public VisibleChooseEv(String attacker, ArrayList<String> valid, ArrayList<String> notselectable, ArrayList<String> notreachable) {
        this.attacker = attacker;
        this.valid = valid;
        this.notselectable = notselectable;
        this.notreachable = notreachable;
    }

    /**
     * @param attacker is the player that invoke the effect
     * @param valid is the list of the players that can be selected as a valid target
     * @param notreachable is the list of the players that can't be selected as target due to be out of the effect's range
     */
    public VisibleChooseEv(String attacker, ArrayList<String> valid, ArrayList<String> notreachable) {
        this.attacker = attacker;
        this.valid = valid;
        this.notreachable = notreachable;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    @Override
    public String getNickname(){
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
}
