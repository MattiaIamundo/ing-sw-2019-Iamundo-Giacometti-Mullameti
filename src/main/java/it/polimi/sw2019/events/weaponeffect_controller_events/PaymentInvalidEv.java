package it.polimi.sw2019.events.weaponeffect_controller_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;
import it.polimi.sw2019.events.NotifyReturn;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentInvalidEv implements NotifyReturn {
    private String player;
    private HashMap<String, Integer> unpaidCost;
    private ArrayList<String> chosenEffects;
    private HashMap<String, String> chosenPowerUps;

    public PaymentInvalidEv(String player, HashMap<String, Integer> unpaidCost, ArrayList<String> chosenEffects, HashMap<String, String> chosenPowerUps) {
        this.player = player;
        this.unpaidCost = unpaidCost;
        this.chosenEffects = chosenEffects;
        this.chosenPowerUps = chosenPowerUps;
    }

    @Override
    public void setNickname(String nickname) {
        player = nickname;
    }

    @Override
    public String getNickname() {
        return player;
    }

    public HashMap<String, Integer> getUnpaidCost() {
        return unpaidCost;
    }

    public ArrayList<String> getChosenEffects() {
        return chosenEffects;
    }

    public HashMap<String, String> getChosenPowerUps() {
        return chosenPowerUps;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
