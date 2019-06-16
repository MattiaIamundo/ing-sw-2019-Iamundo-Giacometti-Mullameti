package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

import java.util.ArrayList;
import java.util.Map;

public interface WithMove {

    void changePosition(Player attacker, ArrayList<String> positions);

    void setMoveto(Space position);
}
