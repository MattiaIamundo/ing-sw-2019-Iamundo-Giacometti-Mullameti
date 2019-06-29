package it.polimi.sw2019.events.powerup_events;

import java.util.ArrayList;

public class TeleporterChooseEv implements PowerUpChooseEv{
    private String user;
    private ArrayList<String> players;
    private ArrayList<String> positions;

    public TeleporterChooseEv(String user, ArrayList<String> players, ArrayList<String> positions) {
        this.user = user;
        this.players = players;
        this.positions = positions;
    }

    @Override
    public String getUser() {
        return user;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public ArrayList<String> getPositions() {
        return positions;
    }
}
