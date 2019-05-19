package it.polimi.sw2019.model.weapon_power;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

import java.util.Map;

public interface WithMove extends Power {
    void changePosition(Player attacker, Map<String, Space> positions);

    void setNewPosition(Space position);
}
