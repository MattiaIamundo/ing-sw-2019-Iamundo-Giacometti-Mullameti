package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.MoveTargetSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.WithMove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class MovePlayer implements EffectController {
    private WithMove model;
    protected Player attacker;
    private Map<String, Space> positions = new HashMap<>();

    public MovePlayer(Power model) {
        this.model = (WithMove) model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquirePosition(attacker.getPosition(), false);
        }
    }

    public void acquirePosition(Space playerpos, boolean singlespace){
        if (singlespace){
            acquireSingle(playerpos);
        }else {
            acquireNorth(playerpos);
            acquireWest(playerpos);
            acquireSouth(playerpos);
            acquireEast(playerpos);
        }
        model.changePosition(attacker, new ArrayList<>(positions.keySet()));
    }

    private void acquireNorth(Space playerpos){
        if (!playerpos.getNorth().isWall()){
            positions.put("north", playerpos.getNorth().getSpaceSecond());
            if (!playerpos.getNorth().getSpaceSecond().getNorth().isWall()){
                positions.put("north-north", playerpos.getNorth().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if (!playerpos.getNorth().getSpaceSecond().getWest().isWall()){
                positions.put("north-west", playerpos.getNorth().getSpaceSecond().getWest().getSpaceSecond());
            }
            if (!playerpos.getNorth().getSpaceSecond().getEast().isWall()){
                positions.put("north-east", playerpos.getNorth().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
    }

    private void acquireWest(Space playerpos){
        if (!playerpos.getWest().isWall()){
            positions.put("west", playerpos.getWest().getSpaceSecond());
            if (!playerpos.getWest().getSpaceSecond().getWest().isWall()){
                positions.put("west-west", playerpos.getWest().getSpaceSecond().getWest().getSpaceSecond());
            }
            if (!playerpos.getWest().getSpaceSecond().getNorth().isWall() && !positions.containsValue(playerpos.getWest().getSpaceSecond().getNorth().getSpaceSecond())){
                positions.put("west-north", playerpos.getWest().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if (!playerpos.getWest().getSpaceSecond().getSouth().isWall() && !positions.containsValue(playerpos.getWest().getSpaceSecond().getSouth().getSpaceSecond())){
                positions.put("west-south", playerpos.getWest().getSpaceSecond().getSouth().getSpaceSecond());
            }
        }
    }

    private void acquireSouth(Space playerpos){
        if (!playerpos.getSouth().isWall()){
            positions.put("south", playerpos.getSouth().getSpaceSecond());
            if (!playerpos.getSouth().getSpaceSecond().getSouth().isWall()){
                positions.put("south-south", playerpos.getSouth().getSpaceSecond().getSouth().getSpaceSecond());
            }
            if (!playerpos.getSouth().getSpaceSecond().getWest().isWall() && !positions.containsValue(playerpos.getSouth().getSpaceSecond().getWest().getSpaceSecond())){
                positions.put("south-west", playerpos.getSouth().getSpaceSecond().getWest().getSpaceSecond());
            }
            if (!playerpos.getSouth().getSpaceSecond().getEast().isWall() && !positions.containsValue(playerpos.getSouth().getSpaceSecond().getEast().getSpaceSecond())){
                positions.put("south-east", playerpos.getSouth().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
    }

    private void acquireEast(Space playerpos){
        if (!playerpos.getEast().isWall()){
            positions.put("east", playerpos.getEast().getSpaceSecond());
            if (!playerpos.getEast().getSpaceSecond().getEast().isWall()){
                positions.put("east-east", playerpos.getEast().getSpaceSecond().getEast().getSpaceSecond());
            }
            if (!playerpos.getEast().getSpaceSecond().getSouth().isWall() && !positions.containsValue(playerpos.getEast().getSpaceSecond().getSouth().getSpaceSecond())){
                positions.put("east-south", playerpos.getEast().getSpaceSecond().getSouth().getSpaceSecond());
            }
            if (!playerpos.getEast().getSpaceSecond().getNorth().isWall() && !positions.containsValue(playerpos.getEast().getSpaceSecond().getNorth().getSpaceSecond())){
                positions.put("east-north", playerpos.getEast().getSpaceSecond().getNorth().getSpaceSecond());
            }
        }
    }

    private void acquireSingle(Space playerpos){
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
        model.changePosition(attacker, new ArrayList<>(positions.keySet()));
    }

    public void update(MoveTargetSetEv message) {
        model.setMoveto(positions.get(message.getMoveto()));
    }
}
