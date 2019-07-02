package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weaponeffect_controller_events.CozyFireModeSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.CozyFireMode;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class CozyFireModeCont implements Observer<CozyFireModeSetEv>, EffectController {
    private CozyFireMode model;
    private Player attacker;
    private ArrayList<Player> players;
    private Map map;
    private HashMap<String, Space> valid = new HashMap<>();

    public CozyFireModeCont(Power model) {
        this.model = (CozyFireMode) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireSquare();
    }

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
        model.chooseTargetArea(attacker, new ArrayList<>(valid.keySet()));
    }

    private Boolean thereIsTargets(Space space){
        for (Player player : players){
            if (player.getPosition() == space){
                return true;
            }
        }
        return false;
    }

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
