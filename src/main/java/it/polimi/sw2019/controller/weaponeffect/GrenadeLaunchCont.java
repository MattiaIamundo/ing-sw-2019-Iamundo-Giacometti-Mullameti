package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.events.GrenadeLaunchSetEv;
import it.polimi.sw2019.model.weapon_power.GrenadeLauncher;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class GrenadeLaunchCont extends SingleTargetCont implements Observer<GrenadeLaunchSetEv>{

    private GrenadeLauncher model;
    private HashMap<String, Space> squares = new HashMap<>();

    public GrenadeLaunchCont(GrenadeLauncher model){
        super(model);
    }

    @Override
    public void acquireTarget() {
        super.acquireTarget();
        acquireSquares(attacker.getPosition());
        model.chooseTarget(attacker, valid, notreachable, new ArrayList<>(squares.keySet()));
    }

    private void acquireSquares(Space attpos){
        if (!attpos.getNorth().isWall()) {
            squares.put("north", attpos.getNorth().getSpaceSecond());
        }
        if (!attpos.getSouth().isWall()){
            squares.put("south", attpos.getSouth().getSpaceSecond());
        }
        if (!attpos.getWest().isWall()){
            squares.put("west", attpos.getWest().getSpaceSecond());
        }
        if (!attpos.getEast().isWall()){
            squares.put("east", attpos.getEast().getSpaceSecond());
        }
    }

    @Override
    public void update(GrenadeLaunchSetEv message) {
        super.update(message);
        model.setMoveto(squares.get(message.getMoveto()));
        model.usePower(attacker);
    }
}
