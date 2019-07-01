package it.polimi.sw2019.events;

import it.polimi.sw2019.controller.Game;

import java.util.ArrayList;

public class WeaponChooseEv implements NotifyReturn{
    private String attacker;
    private ArrayList<String> weapons;

    public WeaponChooseEv(String attacker, ArrayList<String> weapons) {
        this.attacker = attacker;
        this.weapons = weapons;
    }

    @Override
    public void setNickname(String nickname) {
        attacker = nickname;
    }

    public String getNickname() {
        return attacker;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }

    public ArrayList<String> getWeapons() {
        return weapons;
    }
}
