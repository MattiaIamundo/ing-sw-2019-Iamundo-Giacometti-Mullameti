package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weaponEffectController_events.CozyFireModeSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.CozyFireMode;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class CozyFireModeCont implements Observer<CozyFireModeSetEv>, EffectController {
    private CozyFireMode model;
    private Player attacker;
    private ArrayList<Player> players;
    private Map map;
    private HashMap<String, Space> valid = new HashMap<>();

    public CozyFireModeCont(Power model) {
        this.model = (CozyFireMode) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireSquare();
    }

    private void acquireSquare(){
        if (!attacker.getPosition().getNorth().isWall()){
            valid.put("north", attacker.getPosition().getNorth().getSpaceSecond());
        }
        if (!attacker.getPosition().getWest().isWall()){
            valid.put("west", attacker.getPosition().getWest().getSpaceSecond());
        }
        if (!attacker.getPosition().getSouth().isWall()){
            valid.put("south", attacker.getPosition().getSouth().getSpaceSecond());
        }
        if (!attacker.getPosition().getEast().isWall()){
            valid.put("east", attacker.getPosition().getEast().getSpaceSecond());
        }
        model.chooseTargetArea(attacker, new ArrayList<>(valid.keySet()));
    }

    @Override
    public void update(CozyFireModeSetEv message) {
        model.setTargetarea(valid.get(message.getDirection()));
        model.usePower(attacker);
    }
}
