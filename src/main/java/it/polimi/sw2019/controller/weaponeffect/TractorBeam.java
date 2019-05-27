package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Events.TracBeamSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.MoveDamage;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TractorBeam implements Observer<TracBeamSetEv> {
   private Map<Player, Map<String, Space>> movingmap = new HashMap<>();
   private ArrayList<String> validroom = new ArrayList<>();
   private MoveDamage model;
   private Player attacker;

    public TractorBeam(MoveDamage model) {
        this.model = model;
    }

    public void acquireTarget(Player attacker) {
        this.attacker = attacker;
        ArrayList<String> notselectable = new ArrayList<>();
        validroom.add(attacker.getPosition().getRoom());
        if (!attacker.getPosition().getNorth().isWall() && !validroom.contains(attacker.getPosition().getNorth().getSpaceSecond().getRoom())){
            validroom.add(attacker.getPosition().getNorth().getSpaceSecond().getRoom());
        }
        if (!attacker.getPosition().getWest().isWall() && !validroom.contains(attacker.getPosition().getWest().getSpaceSecond().getRoom())){
            validroom.add(attacker.getPosition().getWest().getSpaceSecond().getRoom());
        }
        if (!attacker.getPosition().getSouth().isWall() && !validroom.contains(attacker.getPosition().getSouth().getSpaceSecond().getRoom())){
            validroom.add(attacker.getPosition().getSouth().getSpaceSecond().getRoom());
        }
        if (!attacker.getPosition().getEast().isWall() && !validroom.contains(attacker.getPosition().getEast().getSpaceSecond().getRoom())){
            validroom.add(attacker.getPosition().getEast().getSpaceSecond().getRoom());
        }
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i)!=null && Table.getPlayers(i)!=attacker){
                checkTarget(Table.getPlayers(i));
            }
        }
        notselectable.add(attacker.getNickname());
        HashMap<String, ArrayList<String>> valid = new HashMap<>();
        for (Player a:movingmap.keySet()){
            valid.put(a.getNickname(), new ArrayList<String>(movingmap.get(a).keySet()));
        }
        model.chooseTarget(valid, notselectable);
    }

    private void checkTarget(Player target){
        HashMap<String, Space> positions = new HashMap<>();
        if (validroom.contains(target.getPosition().getRoom())){
            positions.put("zero", target.getPosition());
        }
        if ((!target.getPosition().getNorth().isWall()) && (validroom.contains(target.getPosition().getNorth().getSpaceSecond().getRoom()))){
            positions.put("north", target.getPosition().getNorth().getSpaceSecond());
            if ((!target.getPosition().getNorth().getSpaceSecond().getNorth().isWall()) && (validroom.contains(target.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond().getRoom()))){
                positions.put("north-north", target.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if ((!target.getPosition().getNorth().getSpaceSecond().getWest().isWall()) && (validroom.contains(target.getPosition().getNorth().getSpaceSecond().getWest().getSpaceSecond().getRoom()))){
                positions.put("north-west", target.getPosition().getNorth().getSpaceSecond().getWest().getSpaceSecond());
            }
            if ((!target.getPosition().getNorth().getSpaceSecond().getEast().isWall()) && (validroom.contains(target.getPosition().getNorth().getSpaceSecond().getEast().getSpaceSecond().getRoom()))){
                positions.put("north-east", target.getPosition().getNorth().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
        if ((!target.getPosition().getWest().isWall()) && (validroom.contains(target.getPosition().getWest().getSpaceSecond().getRoom()))){
            positions.put("west", target.getPosition().getWest().getSpaceSecond());
            if ((!target.getPosition().getWest().getSpaceSecond().getWest().isWall()) && (validroom.contains(target.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond().getRoom()))){
                positions.put("west-west", target.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond());
            }
            if ((!target.getPosition().getWest().getSpaceSecond().getNorth().isWall()) && (validroom.contains(target.getPosition().getWest().getSpaceSecond().getNorth().getSpaceSecond().getRoom()))){
                positions.put("west-north", target.getPosition().getWest().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if ((!target.getPosition().getWest().getSpaceSecond().getSouth().isWall()) && (validroom.contains(target.getPosition().getWest().getSpaceSecond().getSouth().getSpaceSecond().getRoom()))){
                positions.put("west-south", target.getPosition().getWest().getSpaceSecond().getSouth().getSpaceSecond());
            }
        }
        if ((!target.getPosition().getSouth().isWall()) && (validroom.contains(target.getPosition().getSouth().getSpaceSecond().getRoom()))){
            positions.put("south", target.getPosition().getSouth().getSpaceSecond());
            if ((!target.getPosition().getSouth().getSpaceSecond().getSouth().isWall()) && (validroom.contains(target.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond().getRoom()))){
                positions.put("south-south", target.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond());
            }
            if ((!target.getPosition().getSouth().getSpaceSecond().getWest().isWall()) && (validroom.contains(target.getPosition().getSouth().getSpaceSecond().getWest().getSpaceSecond().getRoom()))){
                positions.put("south-west", target.getPosition().getSouth().getSpaceSecond().getWest().getSpaceSecond());
            }
            if ((!target.getPosition().getSouth().getSpaceSecond().getEast().isWall()) && (validroom.contains(target.getPosition().getSouth().getSpaceSecond().getEast().getSpaceSecond().getRoom()))){
                positions.put("south-east", target.getPosition().getSouth().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
        if ((!target.getPosition().getEast().isWall()) && (validroom.contains(target.getPosition().getEast().getSpaceSecond().getRoom()))){
            positions.put("east", target.getPosition().getEast().getSpaceSecond());
            if ((!target.getPosition().getEast().getSpaceSecond().getEast().isWall()) && (validroom.contains(target.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond().getRoom()))){
                positions.put("east-east", target.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond());
            }
            if ((!target.getPosition().getEast().getSpaceSecond().getNorth().isWall()) && (validroom.contains(target.getPosition().getEast().getSpaceSecond().getNorth().getSpaceSecond().getRoom()))){
                positions.put("east-north", target.getPosition().getEast().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if ((!target.getPosition().getEast().getSpaceSecond().getSouth().isWall()) && (validroom.contains(target.getPosition().getEast().getSpaceSecond().getSouth().getSpaceSecond().getRoom()))){
                positions.put("east-south", target.getPosition().getEast().getSpaceSecond().getSouth().getSpaceSecond());
            }
        }
        if (!positions.isEmpty()){
            movingmap.put(target, positions);
        }
    }

    @Override
    public void update(TracBeamSetEv message) {
        for (Map.Entry<Player, Map<String, Space>> a:movingmap.entrySet()){
            if (a.getKey().getNickname().equals(message.getTarget())){
                model.setTarget(a.getKey(), a.getValue().get(message.getPosition()));
                model.usePower(attacker);
            }
        }
    }
}
