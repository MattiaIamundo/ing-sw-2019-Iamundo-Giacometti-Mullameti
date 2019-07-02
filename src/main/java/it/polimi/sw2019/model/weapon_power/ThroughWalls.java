package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public interface ThroughWalls {

    public void chooseTarget(HashMap<String, ArrayList<String>> targets, Player attacker);
}
