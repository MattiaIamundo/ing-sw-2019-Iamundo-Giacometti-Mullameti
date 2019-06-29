package it.polimi.sw2019.events.powerup_events;

import java.util.ArrayList;
import java.util.HashMap;

public class NewtonChooseEv implements PowerUpChooseEv{
    private String user;
    private HashMap<String, ArrayList<String>> movements;

    public NewtonChooseEv(String user, HashMap<String, ArrayList<String>> movements) {
        this.user = user;
        this.movements = movements;
    }

    @Override
    public String getUser() {
        return user;
    }

    public HashMap<String, ArrayList<String>> getMovements() {
        return movements;
    }
}
