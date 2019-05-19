package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.WithMove;
import it.polimi.sw2019.view.Observer;

import java.util.HashMap;
import java.util.Map;

public class MovePlayer implements Observer<Space> {
    private WithMove model;

    public MovePlayer(WithMove model) {
        this.model = model;
    }

    public void acquirePosition(Player attacker, Space playerpos, boolean samedir){
        Map<String, Space> positions = new HashMap<>();
        if (!playerpos.getNorth().isWall()){
            positions.put("north", playerpos.getNorth().getSpaceSecond());
            if (!playerpos.getNorth().getSpaceSecond().getNorth().isWall()){
                positions.put("north-north", playerpos.getNorth().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if (!samedir && !playerpos.getNorth().getSpaceSecond().getWest().isWall()){
                positions.put("north-west", playerpos.getNorth().getSpaceSecond().getWest().getSpaceSecond());
            }
            if (!samedir && !playerpos.getNorth().getSpaceSecond().getEast().isWall()){
                positions.put("north-east", playerpos.getNorth().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
        if (!playerpos.getWest().isWall()){
            positions.put("west", playerpos.getWest().getSpaceSecond());
            if (!playerpos.getWest().getSpaceSecond().getWest().isWall()){
                positions.put("west-west", playerpos.getWest().getSpaceSecond().getWest().getSpaceSecond());
            }
            if (!samedir && !playerpos.getWest().getSpaceSecond().getNorth().isWall() && !positions.containsValue(playerpos.getWest().getSpaceSecond().getNorth().getSpaceSecond())){
                positions.put("west-north", playerpos.getWest().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if (!samedir && !playerpos.getWest().getSpaceSecond().getSouth().isWall() && !positions.containsValue(playerpos.getWest().getSpaceSecond().getSouth().getSpaceSecond())){
                positions.put("west-south", playerpos.getWest().getSpaceSecond().getSouth().getSpaceSecond());
            }
        }
        if (!playerpos.getSouth().isWall()){
            positions.put("south", playerpos.getSouth().getSpaceSecond());
            if (!playerpos.getSouth().getSpaceSecond().getSouth().isWall()){
                positions.put("south-south", playerpos.getSouth().getSpaceSecond().getSouth().getSpaceSecond());
            }
            if (!samedir && !playerpos.getSouth().getSpaceSecond().getWest().isWall() && !positions.containsValue(playerpos.getSouth().getSpaceSecond().getWest().getSpaceSecond())){
                positions.put("south-west", playerpos.getSouth().getSpaceSecond().getWest().getSpaceSecond());
            }
            if (!samedir && !playerpos.getSouth().getSpaceSecond().getEast().isWall() && !positions.containsValue(playerpos.getSouth().getSpaceSecond().getEast().getSpaceSecond())){
                positions.put("south-east", playerpos.getSouth().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
        if (!playerpos.getEast().isWall()){
            positions.put("east", playerpos.getEast().getSpaceSecond());
            if (!playerpos.getEast().getSpaceSecond().getEast().isWall()){
                positions.put("east-east", playerpos.getEast().getSpaceSecond().getEast().getSpaceSecond());
            }
            if (!samedir && !playerpos.getEast().getSpaceSecond().getSouth().isWall() && !positions.containsValue(playerpos.getEast().getSpaceSecond().getSouth().getSpaceSecond())){
                positions.put("east-south", playerpos.getEast().getSpaceSecond().getSouth().getSpaceSecond());
            }
            if (!samedir && !playerpos.getEast().getSpaceSecond().getNorth().isWall() && !positions.containsValue(playerpos.getEast().getSpaceSecond().getNorth().getSpaceSecond())){
                positions.put("east-north", playerpos.getEast().getSpaceSecond().getNorth().getSpaceSecond());
            }
        }
        model.changePosition(attacker, positions);
    }

    public void acquirePosition(Player attacker, Space playerpos){
        Map<String, Space> positions = new HashMap<>();
        if (!playerpos.getNorth().isWall()){
            positions.put("north", playerpos.getNorth().getSpaceSecond());
        }
        if (!playerpos.getWest().isWall()){
            positions.put("west", playerpos.getWest().getSpaceSecond());
        }
        if (!playerpos.getSouth().isWall()){
            positions.put("south", playerpos.getSouth().getSpaceSecond());
        }
        if (!playerpos.getEast().isWall()){
            positions.put("east", playerpos.getEast().getSpaceSecond());
        }
        model.changePosition(attacker, positions);
    }

    @Override
    public void update(Space message) {
        model.setNewPosition(message);
    }
}
