package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponEffectController_events.GrenadeLaunchSetEv;
import it.polimi.sw2019.model.weapon_power.GrenadeLauncher;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GrenadeLaunchCont extends VisibleTargetCont implements Observer<GrenadeLaunchSetEv>{

    private GrenadeLauncher realmodel;
    private HashMap<Player, HashMap<String, Space>> squares = new HashMap<>();

    public GrenadeLaunchCont(Power realmodel){
        super(realmodel);
        this.realmodel = (GrenadeLauncher) realmodel;
    }

    @Override
    public void acquireTarget() {
        HashMap<String, ArrayList<String>> moveto = new HashMap<>();

        super.acquireTarget();
        for (Player player : players){
            if (valid.contains(player.getNickname())){
                acquireSquares(player);
            }
        }
        for (Map.Entry<Player, HashMap<String, Space>> mapEntry : squares.entrySet()){
            moveto.put(mapEntry.getKey().getNickname(), new ArrayList<>(mapEntry.getValue().keySet()));
        }
        realmodel.chooseTarget(attacker, valid, notreachable, moveto);
    }

    private void acquireSquares(Player target){
        HashMap<String, Space> directions = new HashMap<>();

        if (!target.getPosition().getNorth().isWall()){
            directions.put("north", target.getPosition().getNorth().getSpaceSecond());
        }
        if (!target.getPosition().getWest().isWall()){
            directions.put("west", target.getPosition().getWest().getSpaceSecond());
        }
        if (!target.getPosition().getSouth().isWall()){
            directions.put("south", target.getPosition().getSouth().getSpaceSecond());
        }
        if (!target.getPosition().getEast().isWall()){
            directions.put("east", target.getPosition().getEast().getSpaceSecond());
        }
        if (!directions.isEmpty()){
            squares.put(target, directions);
        }
    }

    @Override
    public void update(GrenadeLaunchSetEv message) {
        super.update(message);
        realmodel.setMoveto(squares.get(realmodel.getTarget()).get(message.getMoveto()));
        realmodel.usePower(attacker);
    }
}
