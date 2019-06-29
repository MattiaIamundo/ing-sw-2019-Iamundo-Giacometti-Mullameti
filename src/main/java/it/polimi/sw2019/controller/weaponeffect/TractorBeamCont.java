package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.TractorBeamSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.TractorBeam;
import it.polimi.sw2019.view.Observer;

import java.util.*;

public class TractorBeamCont implements Observer<TractorBeamSetEv>, EffectController{

   private Map<Player, Map<String, Space>> movingmap = new HashMap<>();
   private ArrayList<String> validroom = new ArrayList<>();
   private TractorBeam model;
   private Player attacker;
   private ArrayList<Player> players;

    public TractorBeamCont(Power model) {
        this.model = (TractorBeam) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, it.polimi.sw2019.model.Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        acquireTarget();
    }

    private void acquireTarget() {
        initializeRooms();
        for (Player player : players){
            if (player != attacker){
                checkTarget(player);
            }
        }
        HashMap<String, ArrayList<String>> valid = new HashMap<>();
        for (Map.Entry<Player, Map<String, Space>> a : movingmap.entrySet()){
            valid.put(a.getKey().getNickname(), new ArrayList<>(a.getValue().keySet()));
        }
        model.chooseTarget(valid, attacker);
    }

    private void initializeRooms(){
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
    }

    private void checkTarget(Player target){
        HashMap<String, Space> positions = new HashMap<>();
        if (validroom.contains(target.getPosition().getRoom())){
            positions.put("zero", target.getPosition());
        }
        checkNorth(positions, target);
        checkWest(positions, target);
        checkSouth(positions, target);
        checkEast(positions, target);
        if (!positions.isEmpty()){
            movingmap.put(target, positions);
        }
    }

    private void checkNorth(HashMap<String, Space> positions, Player target){
        if (!target.getPosition().getNorth().isWall()){
            if (validroom.contains(target.getPosition().getNorth().getSpaceSecond().getRoom())) {
                positions.put("north", target.getPosition().getNorth().getSpaceSecond());
            }
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
    }

    private void checkWest(HashMap<String, Space> positions, Player target){
        if (!target.getPosition().getWest().isWall()){
            if (validroom.contains(target.getPosition().getWest().getSpaceSecond().getRoom())) {
                positions.put("west", target.getPosition().getWest().getSpaceSecond());
            }
            if ((!target.getPosition().getWest().getSpaceSecond().getWest().isWall()) && (validroom.contains(target.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond().getRoom()))){
                positions.put("west-west", target.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond());
            }
            if ((!target.getPosition().getWest().getSpaceSecond().getNorth().isWall()) && (validroom.contains(target.getPosition().getWest().getSpaceSecond().getNorth().getSpaceSecond().getRoom()))
                    && (!positions.containsValue(target.getPosition().getWest().getSpaceSecond().getNorth().getSpaceSecond()))){
                positions.put("west-north", target.getPosition().getWest().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if ((!target.getPosition().getWest().getSpaceSecond().getSouth().isWall()) && (validroom.contains(target.getPosition().getWest().getSpaceSecond().getSouth().getSpaceSecond().getRoom()))
                    && (!positions.containsValue(target.getPosition().getWest().getSpaceSecond().getSouth().getSpaceSecond()))){
                positions.put("west-south", target.getPosition().getWest().getSpaceSecond().getSouth().getSpaceSecond());
            }
        }
    }

    private void checkSouth(HashMap<String, Space> positions, Player target){
        if (!target.getPosition().getSouth().isWall()){
            if (validroom.contains(target.getPosition().getSouth().getSpaceSecond().getRoom())) {
                positions.put("south", target.getPosition().getSouth().getSpaceSecond());
            }
            if ((!target.getPosition().getSouth().getSpaceSecond().getSouth().isWall()) && (validroom.contains(target.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond().getRoom()))){
                positions.put("south-south", target.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond());
            }
            if ((!target.getPosition().getSouth().getSpaceSecond().getWest().isWall()) && (validroom.contains(target.getPosition().getSouth().getSpaceSecond().getWest().getSpaceSecond().getRoom()))
                    && (!positions.containsValue(target.getPosition().getSouth().getSpaceSecond().getWest().getSpaceSecond()))){
                positions.put("south-west", target.getPosition().getSouth().getSpaceSecond().getWest().getSpaceSecond());
            }
            if ((!target.getPosition().getSouth().getSpaceSecond().getEast().isWall()) && (validroom.contains(target.getPosition().getSouth().getSpaceSecond().getEast().getSpaceSecond().getRoom()))
                    && (!positions.containsValue(target.getPosition().getSouth().getSpaceSecond().getEast().getSpaceSecond()))){
                positions.put("south-east", target.getPosition().getSouth().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
    }

    private void checkEast(HashMap<String, Space> positions, Player target){
        if (!target.getPosition().getEast().isWall()){
            if (validroom.contains(target.getPosition().getEast().getSpaceSecond().getRoom())) {
                positions.put("east", target.getPosition().getEast().getSpaceSecond());
            }
            if ((!target.getPosition().getEast().getSpaceSecond().getEast().isWall()) && (validroom.contains(target.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond().getRoom()))){
                positions.put("east-east", target.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond());
            }
            if ((!target.getPosition().getEast().getSpaceSecond().getNorth().isWall()) && (validroom.contains(target.getPosition().getEast().getSpaceSecond().getNorth().getSpaceSecond().getRoom()))
                    && (!positions.containsValue(target.getPosition().getEast().getSpaceSecond().getNorth().getSpaceSecond()))){
                positions.put("east-north", target.getPosition().getEast().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if ((!target.getPosition().getEast().getSpaceSecond().getSouth().isWall()) && (validroom.contains(target.getPosition().getEast().getSpaceSecond().getSouth().getSpaceSecond().getRoom()))
                    && (!positions.containsValue(target.getPosition().getEast().getSpaceSecond().getSouth().getSpaceSecond()))){
                positions.put("east-south", target.getPosition().getEast().getSpaceSecond().getSouth().getSpaceSecond());
            }
        }
    }

    @Override
    public void update(TractorBeamSetEv message) {
        for (Map.Entry<Player, Map<String, Space>> a:movingmap.entrySet()){
            if (a.getKey().getNickname().equals(message.getTarget())){
                model.setTarget(a.getKey(), a.getValue().get(message.getPosition()));
                model.usePower(attacker);
            }
        }
    }
}
