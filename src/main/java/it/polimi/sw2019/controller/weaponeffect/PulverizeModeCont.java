package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Events.PulvModeSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.PulverizeMode;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class PulverizeModeCont implements Observer<PulvModeSetEv>, EffectController {
    private PulverizeMode model;
    private Player attacker;
    private HashMap<String, Space> positions = new HashMap<>();

    public PulverizeModeCont(PulverizeMode model) {
        this.model = model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTarget();
        }
    }

    private void acquireTarget(){
        ArrayList<String> targets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker) && (Table.getPlayers(i).getPosition() == attacker.getPosition())){
                targets.add(Table.getPlayers(i).getNickname());
            }
        }
        initializePositions();
        model.chooseTarget(targets, new ArrayList<>(positions.keySet()));
    }

    private void initializePositions(){
        positions.put("zero", attacker.getPosition());
        if (!attacker.getPosition().getNorth().isWall()){
            positions.put("north", attacker.getPosition().getNorth().getSpaceSecond());
            if (!attacker.getPosition().getNorth().getSpaceSecond().getNorth().isWall()){
                positions.put("north-north", attacker.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond());
            }
        }
        if (!attacker.getPosition().getWest().isWall()){
            positions.put("west", attacker.getPosition().getWest().getSpaceSecond());
            if (!attacker.getPosition().getWest().getSpaceSecond().getWest().isWall()){
                positions.put("west-west", attacker.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond());
            }
        }
        if (!attacker.getPosition().getSouth().isWall()){
            positions.put("south", attacker.getPosition().getSouth().getSpaceSecond());
            if (!attacker.getPosition().getSouth().getSpaceSecond().getSouth().isWall()){
                positions.put("south-south", attacker.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond());
            }
        }
        if (!attacker.getPosition().getEast().isWall()){
            positions.put("east", attacker.getPosition().getEast().getSpaceSecond());
            if (!attacker.getPosition().getEast().getSpaceSecond().getEast().isWall()){
                positions.put("east-east", attacker.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
    }

    @Override
    public void update(PulvModeSetEv message) {
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getNickname().equals(message.getTarget())){
                model.setTarget(Table.getPlayers(i));
            }
        }
        model.setMoveto(positions.get(message.getMoveto()));
    }
}
