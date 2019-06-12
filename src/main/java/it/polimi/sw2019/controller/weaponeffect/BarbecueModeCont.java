package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.events.BarbecueSetEv;
import it.polimi.sw2019.model.weapon_power.BarbecueMode;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class BarbecueModeCont implements Observer<BarbecueSetEv>, EffectController {

    private BarbecueMode model;
    private Player attacker;
    private HashMap<String, ArrayList<Space>> directions = new HashMap<>();

    public BarbecueModeCont(BarbecueMode model) {
        this.model = model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireDirections();
        }
    }

    private void acquireDirections(){
        loadNorth();
        loadWest();
        loadSouth();
        loadEast();
        model.chooseDirection(attacker, new ArrayList<>(directions.keySet()));
    }

    private void loadNorth(){
        ArrayList<Space> valid = new ArrayList<>();
        if (!attacker.getPosition().getNorth().isWall()){
            valid.add(attacker.getPosition().getNorth().getSpaceSecond());
            if (!attacker.getPosition().getNorth().getSpaceSecond().getNorth().isWall()){
                valid.add(attacker.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond());
            }
            directions.put("north", valid);
        }
    }

    private void loadWest(){
        ArrayList<Space> valid = new ArrayList<>();
        if (!attacker.getPosition().getWest().isWall()){
            valid.add( attacker.getPosition().getWest().getSpaceSecond());
            if (!attacker.getPosition().getWest().getSpaceSecond().getWest().isWall()){
                valid.add(attacker.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond());
            }
            directions.put("west", valid);
        }
    }

    private void loadSouth(){
        ArrayList<Space> valid = new ArrayList<>();
        if (!attacker.getPosition().getSouth().isWall()){
            valid.add(attacker.getPosition().getSouth().getSpaceSecond());
            if (!attacker.getPosition().getSouth().getSpaceSecond().getSouth().isWall()){
                valid.add(attacker.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond());
            }
            directions.put("south", valid);
        }
    }

    private void loadEast(){
        ArrayList<Space> valid = new ArrayList<>();
        if (!attacker.getPosition().getEast().isWall()){
            valid.add(attacker.getPosition().getEast().getSpaceSecond());
            if (!attacker.getPosition().getEast().getSpaceSecond().getEast().isWall()){
                valid.add(attacker.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond());
            }
            directions.put("south", valid);
        }
    }

    @Override
    public void update(BarbecueSetEv message) {
        ArrayList<Space> positions = directions.get(message.getDirection());
        model.setTargetarea1(positions.get(0));
        if (positions.size() == 2){
            model.setTargetarea2(positions.get(1));
        }else {
            model.setTargetarea2(null);
        }
        model.usePower(attacker);
    }
}
