package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.RocketLaunchSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.RocketLauncher;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class RocketLaunchCont implements Observer<RocketLaunchSetEv>, EffectController{

    private RocketLauncher model;
    private Player attacker;
    private HashMap<String, HashMap<String, Space>> targets = new HashMap<>();

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTarget();
        }
    }

    public void acquireTarget() {
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker) && (Table.getPlayers(i).isVisible(attacker)) && (Table.getPlayers(i).getPosition() != attacker.getPosition())){
                targets.put(Table.getPlayers(i).getNickname(), acquireMovements(Table.getPlayers(i).getPosition()));
            }
        }
        HashMap<String, ArrayList<String>> valid = new HashMap<>();
        for (HashMap.Entry<String, HashMap<String, Space>> a : targets.entrySet()){
            valid.put(a.getKey(), new ArrayList<>(a.getValue().keySet()));
        }
        model.chooseTarget(valid, attacker);
    }

    private HashMap<String, Space> acquireMovements(Space tarpos){
        HashMap<String, Space> positions = new HashMap<>();

        positions.put("basic", tarpos);
        if (!tarpos.getEast().isWall()){
            positions.put("east", tarpos.getEast().getSpaceSecond());
        }
        if (!tarpos.getWest().isWall()){
            positions.put("west", tarpos.getWest().getSpaceSecond());
        }
        if (!tarpos.getNorth().isWall()){
            positions.put("north", tarpos.getNorth().getSpaceSecond());
        }
        if (!tarpos.getSouth().isWall()){
            positions.put("south", tarpos.getSouth().getSpaceSecond());
        }
        return positions;
    }

    @Override
    public void update(RocketLaunchSetEv message) {
        int i = 0;
        Player target;
        Space position;

        while ((i < 5) && (!Table.getPlayers(i).getNickname().equals(message.getTarget()))){
            i++;
        }
        target = Table.getPlayers(i);
        position = targets.get(message.getTarget()).get(message.getPosition());
        model.setTarget(target, position);
        model.usePower(attacker);
    }
}
