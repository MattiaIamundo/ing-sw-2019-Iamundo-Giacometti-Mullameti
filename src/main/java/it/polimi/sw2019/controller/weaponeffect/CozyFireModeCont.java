package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.CozyFireModeSetEv;
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
    private HashMap<String, Space> valid = new HashMap<>();

    public CozyFireModeCont(Power model) {
        this.model = (CozyFireMode) model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireSquare();
        }
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
