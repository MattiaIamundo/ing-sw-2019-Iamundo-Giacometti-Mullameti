package it.polimi.sw2019.model.Events;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

import java.util.Map;

public class PositionSelctEv implements WeaponEvent{
    private Player attacker;
    private Map<String, Space> positions;
    private String message;

    public PositionSelctEv(Player attacker, Map<String, Space> positions, String message) {
        this.attacker = attacker;
        this.positions = positions;
        this.message = message;
    }

    public Player getAttacker() {
        return attacker;
    }

    public Map<String, Space> getPositions() {
        return positions;
    }

    public String getMessage() {
        return message;
    }
}
