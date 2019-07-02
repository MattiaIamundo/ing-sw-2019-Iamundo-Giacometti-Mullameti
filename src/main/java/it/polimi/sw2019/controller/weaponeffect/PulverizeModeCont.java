package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.PulvModeChooseEv;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weaponeffect_controller_events.PulvModeSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.PulverizeMode;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class PulverizeModeCont extends EffectController implements Observer<PulvModeSetEv> {

    private PulverizeMode model;
    private Player attacker;
    private ArrayList<Player> players;
    private HashMap<String, Space> positions = new HashMap<>();

    public PulverizeModeCont(Power model) {
        this.model = (PulverizeMode) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        acquireTarget();
    }

    private void acquireTarget(){
        ArrayList<String> targets = new ArrayList<>();
        for (Player player : players){
            if ((player != attacker) && (player.getPosition() == attacker.getPosition())){
                targets.add(player.getNickname());
            }
        }
        initializePositions();
        notify(new PulvModeChooseEv(attacker.getNickname(), targets, new ArrayList<>(positions.keySet())));
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
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget())){
                model.setTarget(player);
                break;
            }
        }
        model.setMoveto(positions.get(message.getMoveto()));
        model.usePower(attacker);
    }
}
