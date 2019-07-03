package it.polimi.sw2019.events.weapon_event;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.utility.SimplifiedPowerUp;

import java.util.ArrayList;
import java.util.HashMap;

public class PowerChooseEv implements NotifyReturn {
    private String nickname;
    private Boolean itsAlternative;
    private HashMap<String,ArrayList<String>> powers;
    private HashMap<String, Integer> availableAmmo;
    private ArrayList<SimplifiedPowerUp> availablePowerUps;

    public PowerChooseEv(String nickname, Boolean itsAlternative, HashMap<String, ArrayList<String>> powers, HashMap<String, Integer> availableAmmo, ArrayList<SimplifiedPowerUp> availablePowerUps) {
        this.nickname = nickname;
        this.itsAlternative = itsAlternative;
        this.powers = powers;
        this.availableAmmo = availableAmmo;
        this.availablePowerUps = availablePowerUps;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public ArrayList<SimplifiedPowerUp> getAvailablePowerUps() {
        return availablePowerUps;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
