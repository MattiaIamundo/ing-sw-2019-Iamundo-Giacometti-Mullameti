package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;

import java.util.ArrayList;
import java.util.HashMap;

public class PowerChooseEv implements NotifyReturn {
    private String player;
    private Boolean itsAlternative;
    private HashMap<String,ArrayList<String>> powers;
    private HashMap<String, Integer> availableAmmo;
    private HashMap<String, String> availablePowerUps;

    public PowerChooseEv(String player, Boolean itsAlternative, HashMap<String, ArrayList<String>> powers, HashMap<String, Integer> availableAmmo, HashMap<String, String> availablePowerUps) {
        this.player = player;
        this.itsAlternative = itsAlternative;
        this.powers = powers;
        this.availableAmmo = availableAmmo;
        this.availablePowerUps = availablePowerUps;
    }

    @Override
    public void setNickname(String nickname) {
        player = nickname;
    }

    @Override
    public String getNickname() {
        return player;
    }

    public Boolean getItsAlternative() {
        return itsAlternative;
    }

    public HashMap<String, ArrayList<String>> getPowers() {
        return powers;
    }

    public HashMap<String, Integer> getAvailableAmmo() {
        return availableAmmo;
    }

    public HashMap<String, String> getAvailablePowerUps() {
        return availablePowerUps;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
