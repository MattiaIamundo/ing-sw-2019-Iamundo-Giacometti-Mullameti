package it.polimi.sw2019.events.weapon_event;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;

import java.util.ArrayList;
import java.util.HashMap;

public class UnpaidEffectEv implements NotifyReturn {
    private String nickname;
    private ArrayList<String> selectedEffects;
    private HashMap<String, String> usedPowerUps;
    private HashMap<String, Integer> remainingCost;
    private HashMap<String, String> availablePowerUps;
    private HashMap<String, Integer> availableAmmo;

    public UnpaidEffectEv(String nickname, ArrayList<String> selectedEffects, HashMap<String, String> usedPowerUps, HashMap<String, Integer> remainingCost, HashMap<String, String> availablePowerUps, HashMap<String, Integer> availableAmmo) {
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

    public HashMap<String, String> getUsedPowerUps() {
        return usedPowerUps;
    }

    public HashMap<String, Integer> getRemainingCost() {
        return remainingCost;
    }

    public HashMap<String, String> getAvailablePowerUps() {
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
