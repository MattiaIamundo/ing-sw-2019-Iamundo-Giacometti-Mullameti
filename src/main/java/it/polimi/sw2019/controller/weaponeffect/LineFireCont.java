package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.WeaponEvent;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.LineFireSetEv;
import it.polimi.sw2019.model.weapon_power.LineFire;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represent the controller for all the effect that shot on a determinate direction
 */
public abstract class LineFireCont extends EffectController {
    protected LineFire model;
    protected Player attacker;
    protected ArrayList<Player> players;
    protected Map map;
    protected HashMap<String, ArrayList<String>> firststep = new HashMap<>();
    protected HashMap<String, ArrayList<String>> secondstep = new HashMap<>();

    /**
     * @param model the generic model of this type of effect
     */
    public LineFireCont(Power model) {
        this.model = (LineFire) model;
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
        this.map = gamemap;
        acquireTargets();
    }

    /**
     * This method check which are the valid directions
     */
    protected void acquireTargets(){
        loadNorth();
        loadWest();
        loadSouth();
        loadEast();
    }

    /**
     * This method check if it's possible to shoot on north
     */
    private void loadNorth(){
        ArrayList<String> first = new ArrayList<>();

        if (!attacker.getPosition().getNorth().isWall()){
            for (Player player : players){
                if (player.getPosition() == attacker.getPosition().getNorth().getSpaceSecond()){
                    first.add(player.getNickname());
                }
            }
            firststep.put("north", first);
            loadNorthSecond();
        }
    }

    /**
     * This method check if it's possible to shoot on north-north
     */
    private void loadNorthSecond(){
        ArrayList<String> second = new ArrayList<>();

        if (!attacker.getPosition().getNorth().getSpaceSecond().getNorth().isWall()) {
            for (Player player : players) {
                if (player.getPosition() == attacker.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond()) {
                    second.add(player.getNickname());
                }
            }
            secondstep.put("north", second);
        }
    }

    /**
     * This method check if it's possible to shoot on west
     */
    private void loadWest(){
        ArrayList<String> first = new ArrayList<>();

        if (!attacker.getPosition().getWest().isWall()){
            for (Player player : players){
                if (player.getPosition() == attacker.getPosition().getWest().getSpaceSecond()){
                    first.add(player.getNickname());
                }
            }
            firststep.put("west", first);
            loadWestSecond();
        }
    }

    /**
     * This method check if it's possible to shoot on west-west
     */
    private void loadWestSecond(){
        ArrayList<String> second = new ArrayList<>();

        if (!attacker.getPosition().getWest().getSpaceSecond().getWest().isWall()) {
            for (Player player : players) {
                if (player.getPosition() == attacker.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond()) {
                    second.add(player.getNickname());
                }
            }
            secondstep.put("west", second);
        }
    }

    /**
     * This method check if it's possible to shoot on south
     */
    private void loadSouth(){
        ArrayList<String> first = new ArrayList<>();

        if (!attacker.getPosition().getSouth().isWall()){
            for (Player player : players){
                if (player.getPosition() == attacker.getPosition().getSouth().getSpaceSecond()){
                    first.add(player.getNickname());
                }
            }
            firststep.put("south", first);
            loadSouthSecond();
        }
    }

    /**
     * This method check if it's possible to shoot on south-south
     */
    private void loadSouthSecond(){
        ArrayList<String> second = new ArrayList<>();

        if (!attacker.getPosition().getSouth().getSpaceSecond().getSouth().isWall()) {
            for (Player player : players) {
                if (player.getPosition() == attacker.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond()) {
                    second.add(player.getNickname());
                }
            }
            secondstep.put("south", second);
        }
    }

    /**
     * This method check if it's possible to shoot on east
     */
    private void loadEast(){
        ArrayList<String> first = new ArrayList<>();

        if(!attacker.getPosition().getEast().isWall()){
            for (Player player : players){
                if (player.getPosition() == attacker.getPosition().getEast().getSpaceSecond()){
                    first.add(player.getNickname());
                }
            }
            firststep.put("east", first);
            loadEastSecond();
        }
    }

    /**
     * This method check if it's possible to shoot on east-east
     */
    private void loadEastSecond(){
        ArrayList<String> second = new ArrayList<>();

        if (!attacker.getPosition().getEast().getSpaceSecond().getEast().isWall()) {
            for (Player player : players) {
                if (player.getPosition() == attacker.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond()) {
                    second.add(player.getNickname());
                }
            }
            secondstep.put("east", second);
        }
    }

    /**
     * This method catch a LineFireSetEv event
     * @param message the object which have to be updated
     */
    public void update(LineFireSetEv message){
        if (message.getTarget1() != null){
            for (Player player : players){
                if (player.getNickname().equals(message.getTarget1())){
                    model.setTarget1(player);
                    break;
                }
            }
        }else {
            model.setTarget1(null);
        }
        if (message.getTarget2() != null){
            for (Player player : players){
                if (player.getNickname().equals(message.getTarget2())){
                    model.setTarget2(player);
                    break;
                }
            }
        }else {
            model.setTarget2(null);
        }
    }
}
