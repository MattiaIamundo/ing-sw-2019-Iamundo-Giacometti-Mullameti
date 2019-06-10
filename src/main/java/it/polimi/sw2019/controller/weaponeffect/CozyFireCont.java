package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.CozyFireSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.CozyFireMode;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class CozyFireCont implements Observer<CozyFireSetEv>, EffectController {
    private CozyFireMode model;
    private Player attacker;
    HashMap<String, Space> valid = new HashMap<>();

    public CozyFireCont(CozyFireMode model) {
        this.model = model;
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
        model.chooseTargetArea(new ArrayList<>(valid.keySet()), attacker);
    }

    @Override
    public void update(CozyFireSetEv message) {
        model.setTargetarea(valid.get(message.getPosition()));
    }
}
