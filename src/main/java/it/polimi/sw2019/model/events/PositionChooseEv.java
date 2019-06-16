package it.polimi.sw2019.model.events;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;

import java.util.Map;

public class PositionChooseEv implements WeaponEvent{
    private Player attacker;
    private Map<String, Space> positions;
    private String message;

    public PositionChooseEv(Player attacker, Map<String, Space> positions, String message) {
        this.attacker = attacker;
        this.positions = positions;
        this.message = message;
    }

    public String getAttacker() {
        return attacker.getNickname();
    }

    public Map<String, Space> getPositions() {
        return positions;
    }

    public String getMessage() {
        return message;
    }
}
