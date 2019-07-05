package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.BarbecueChooseEv;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponeffect_controller_events.BarbecueSetEv;
import it.polimi.sw2019.model.weapon_power.BarbecueMode;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represent the controller for Barbecue mode, the alternative effect of Flamethrower
 */
public class BarbecueModeCont extends EffectController implements Observer<BarbecueSetEv> {
    private BarbecueMode model;
    private Player attacker;
    private ArrayList<Player> players;
    private HashMap<String, ArrayList<Space>> directions = new HashMap<>();

    /**
     * @param model the model of the effect
     */
    public BarbecueModeCont(Power model) {
        this.model = (BarbecueMode) model;
    }

    /**
     * This method activate the effect
     * @param attacker is the player that invoke the effect
     * @param players is the list of the players in the math
     * @param gamemap is the map of the match
     */
    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        acquireDirections();
    }

    /**
     * This method verify the directions in which the attacker can shoot
     */
    private void acquireDirections(){
        loadNorth();
        loadWest();
        loadSouth();
        loadEast();
        notify(new BarbecueChooseEv(attacker.getNickname(), new ArrayList<>(directions.keySet())));
    }

    /**
     * This method verify if the attacker can shoot on north
     */
    private void loadNorth(){
        ArrayList<Space> valid = new ArrayList<>();
        if (!attacker.getPosition().getNorth().isWall()){
            valid.add(attacker.getPosition().getNorth().getSpaceSecond());
            if (!attacker.getPosition().getNorth().getSpaceSecond().getNorth().isWall()){
                valid.add(attacker.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if (existTargets(valid)) {
                directions.put("north", valid);
            }
        }
    }

    /**
     * This method verify if the attacker can shoot on west
     */
    private void loadWest(){
        ArrayList<Space> valid = new ArrayList<>();
        if (!attacker.getPosition().getWest().isWall()){
            valid.add( attacker.getPosition().getWest().getSpaceSecond());
            if (!attacker.getPosition().getWest().getSpaceSecond().getWest().isWall()){
                valid.add(attacker.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond());
            }
            if (existTargets(valid)) {
                directions.put("west", valid);
            }
        }
    }

    /**
     * This method verify if the attacker can shoot on south
     */
    private void loadSouth(){
        ArrayList<Space> valid = new ArrayList<>();
        if (!attacker.getPosition().getSouth().isWall()){
            valid.add(attacker.getPosition().getSouth().getSpaceSecond());
            if (!attacker.getPosition().getSouth().getSpaceSecond().getSouth().isWall()){
                valid.add(attacker.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond());
            }
            if (existTargets(valid)) {
                directions.put("south", valid);
            }
        }
    }

    /**
     * This method verify if the attacker can shoot on east
     */
    private void loadEast(){
        ArrayList<Space> valid = new ArrayList<>();
        if (!attacker.getPosition().getEast().isWall()){
            valid.add(attacker.getPosition().getEast().getSpaceSecond());
            if (!attacker.getPosition().getEast().getSpaceSecond().getEast().isWall()){
                valid.add(attacker.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond());
            }
            if (existTargets(valid)) {
                directions.put("east", valid);
            }
        }
    }

    /**
     * This method verify if exists some players on the specified direction
     * @param valid is the list of the squares in the specified direction
     * @return true if exist at least one player, false otherwise
     */
    private Boolean existTargets(ArrayList<Space> valid){
        for (Player player : players){
            if (valid.contains(player.getPosition())){
                return true;
            }
        }
        return false;
    }

    /**
     * This method catch a BarbecueMOdeSetEv event
     * @param message the object which have to be updated
     */
    @Override
    public void update(BarbecueSetEv message) {
        ArrayList<Player> targets = new ArrayList<>();
        ArrayList<Space> positions = directions.get(message.getDirection());
        model.setTargetarea1(positions.get(0));
        if (positions.size() == 2){
            model.setTargetarea2(positions.get(1));
        }else {
            model.setTargetarea2(null);
        }
        for (Player player : players){
            if (positions.contains(player.getPosition())){
                targets.add(player);
            }
        }
        model.setTargets(targets);
        model.usePower(attacker);
    }

    public HashMap<String, ArrayList<Space>> getDirections() {
        return directions;
    }
}
