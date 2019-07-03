package it.polimi.sw2019.events.weapon_event;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;
import it.polimi.sw2019.utility.SimplifiedPowerUp;

import java.util.ArrayList;
import java.util.HashMap;

public class UnpaidEffectEv implements NotifyReturn {
    private String nickname;
    private ArrayList<String> selectedEffects;
    private ArrayList<SimplifiedPowerUp> usedPowerUps;
    private HashMap<String, Integer> remainingCost;
    private ArrayList<SimplifiedPowerUp> availablePowerUps;
    private HashMap<String, Integer> availableAmmo;

    public UnpaidEffectEv(String nickname, ArrayList<String> selectedEffects, ArrayList<SimplifiedPowerUp> usedPowerUps, HashMap<String, Integer> remainingCost, ArrayList<SimplifiedPowerUp> availablePowerUps, HashMap<String, Integer> availableAmmo) {
        this.nickname = nickname;
        this.selectedEffects = selectedEffects;
        this.usedPowerUps = usedPowerUps;
        this.remainingCost = remainingCost;
        this.availablePowerUps = availablePowerUps;
        this.availableAmmo = availableAmmo;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<String> getSelectedEffects() {
        return selectedEffects;
    }

    public ArrayList<SimplifiedPowerUp> getUsedPowerUps() {
        return usedPowerUps;
    }

    public HashMap<String, Integer> getRemainingCost() {
        return remainingCost;
    }

    public ArrayList<SimplifiedPowerUp> getAvailablePowerUps() {
        return availablePowerUps;
    }

    public HashMap<String, Integer> getAvailableAmmo() {
        return availableAmmo;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
