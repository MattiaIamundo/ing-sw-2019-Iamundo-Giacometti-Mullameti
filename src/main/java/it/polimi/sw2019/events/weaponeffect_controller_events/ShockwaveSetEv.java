package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ActionEv;
import it.polimi.sw2019.events.ExecutorEventImp;

/**
 * These class represent the set event of Shockwave, the basic effect of Shockwave
 */
public class ShockwaveSetEv implements ActionEv {
    private String  playerNickname;
    private String target1;
    private String target2;
    private String target3;

    /**
     * These is the constructor in case the attacker choose 3 targets
     * @param target1 is the player selected as first target, must be one of those contained in the hash map in the choose event, the direction can be whatever
     * @param target2 is the player selected as second target, must be one of those contained in the hash map in the choose event, the direction must be different from that of the first target
     * @param target3 is the player selected as third target, must be one of those contained in the hash map in the choose event, the direction must be different from that of the first and second target
     */
    public ShockwaveSetEv(String target1, String target2, String target3) {
        this.target1 = target1;
        this.target2 = target2;
        this.target3 = target3;
    }

    /**
     * These is the constructor in case the attacker choose 2 targets
     * @param target1 is the player selected as first target, must be one of those contained in the hash map in the choose event, the direction can be whatever
     * @param target2 is the player selected as second target, must be one of those contained in the hash map in the choose event, the direction must be different from that of the first target
     */
    public ShockwaveSetEv(String target1, String target2) {
        this.target1 = target1;
        this.target2 = target2;
        target3 = null;
    }

    /**
     * These is the constructor in case the attacker choose only one target
     * @param target1 is the player selected as first target, must be one of those contained in the hash map in the choose event, the direction can be whatever
     */
    public ShockwaveSetEv(String target1) {
        this.target1 = target1;
        target2 = null;
        target3 = null;
    }

    @Override
    public String getPlayerNickname() {
        return playerNickname;
    }

    @Override
    public void setPlayerNickname(String playerNickname) {
        this.playerNickname = playerNickname;
    }

    public String getTarget1() {
        return target1;
    }

    public String getTarget2() {
        return target2;
    }

    public String getTarget3() {
        return target3;
    }

    @Override
    public void handle(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.handleObject(this, controller);
    }
}
