package it.polimi.sw2019.events.powerup_events;

import it.polimi.sw2019.controller.Game;
import it.polimi.sw2019.events.ExecutorEventImp;

import java.util.ArrayList;

public class TeleporterChooseEv implements PowerUpEffectChooseEv {
    private String user;
    private ArrayList<String> players;
    private ArrayList<String> positions;

    public TeleporterChooseEv(String user, ArrayList<String> players, ArrayList<String> positions) {
        this.user = user;
        this.players = players;
        this.positions = positions;
    }

    @Override
    public void setNickname(String nickname) {
        user = nickname;
    }

    @Override
    public String getNickname() {
        return user;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public ArrayList<String> getPositions() {
        return positions;
    }

    @Override
    public void updateObject(ExecutorEventImp executorEventImp, Game controller) {
        executorEventImp.updateObject(this, controller);
    }
}
