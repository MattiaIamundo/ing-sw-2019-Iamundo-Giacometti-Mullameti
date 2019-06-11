package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public interface LineFire {

    void chooseTarget(HashMap<String, ArrayList<String>> firstline, HashMap<String, ArrayList<String>> secondline, Player attacker);
    void setTarget1(Player target1);
    void setTarget2(Player target2);
}
