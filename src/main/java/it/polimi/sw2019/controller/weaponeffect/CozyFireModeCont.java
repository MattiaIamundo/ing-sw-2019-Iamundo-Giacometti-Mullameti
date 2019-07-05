package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.CozyFireModeChooseEv;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weaponeffect_controller_events.CozyFireModeSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.CozyFireMode;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represent the controller of Cozy fire mode, the alternative effect of Furnace
 */
public class CozyFireModeCont extends EffectController implements Observer<CozyFireModeSetEv> {
    private CozyFireMode model;
    private Player attacker;
    private ArrayList<Player> players;
    private HashMap<String, Space> valid = new HashMap<>();

    /**
     * @param model the model of the effect
     */
    public CozyFireModeCont(Power model) {
        this.model = (CozyFireMode) model;
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
        acquireSquare();
    }

    /**
     * This method identify which squares can be selected as a valid target
     */
    private void acquireSquare(){
        if ((!attacker.getPosition().getNorth().isWall()) && (thereIsTargets(attacker.getPosition().getNorth().getSpaceSecond()))){
            valid.put("north", attacker.getPosition().getNorth().getSpaceSecond());
        }
        if ((!attacker.getPosition().getWest().isWall()) && (thereIsTargets(attacker.getPosition().getWest().getSpaceSecond()))){
            valid.put("west", attacker.getPosition().getWest().getSpaceSecond());
        }
        if ((!attacker.getPosition().getSouth().isWall()) && (thereIsTargets(attacker.getPosition().getSouth().getSpaceSecond()))){
            valid.put("south", attacker.getPosition().getSouth().getSpaceSecond());
        }
        if ((!attacker.getPosition().getEast().isWall()) && (thereIsTargets(attacker.getPosition().getEast().getSpaceSecond()))){
            valid.put("east", attacker.getPosition().getEast().getSpaceSecond());
        }
        notify(new CozyFireModeChooseEv(attacker.getNickname(), new ArrayList<>(valid.keySet())));
    }

    /**
     * This method check if exist some players on the current square
     * @param space the square under analysis
     * @return true if exist at least one player, false otherwise
     */
    private Boolean thereIsTargets(Space space){
        for (Player player : players){
            if (player.getPosition() == space){
                return true;
            }
        }
        return false;
    }

    /**
     * This method catch a CozyFireSetEv
     * @param message the object which have to be updated
     */
    @Override
    public void update(CozyFireModeSetEv message) {
        Space space = valid.get(message.getDirection());
        ArrayList<Player> targets = new ArrayList<>();
        for (Player player : players){
            if (player.getPosition() == space){
                targets.add(player);
            }
        }
        model.setPlayers(targets);
        model.usePower(attacker);
    }

    public HashMap<String, Space> getValid() {
        return valid;
    }
}
