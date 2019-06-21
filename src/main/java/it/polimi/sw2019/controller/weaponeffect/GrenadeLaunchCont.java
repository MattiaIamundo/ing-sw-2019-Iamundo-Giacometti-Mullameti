package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.events.GrenadeLaunchSetEv;
import it.polimi.sw2019.model.weapon_power.GrenadeLauncher;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class GrenadeLaunchCont extends VisibleTargetCont implements Observer<GrenadeLaunchSetEv>{

    private GrenadeLauncher realmodel;
    private HashMap<String, Space> squares = new HashMap<>();

    public GrenadeLaunchCont(Power realmodel){
        super(realmodel);
        this.realmodel = (GrenadeLauncher) realmodel;
    }

    @Override
    public void acquireTarget() {
        super.acquireTarget();
        acquireSquares(attacker.getPosition());
        realmodel.chooseTarget(attacker, valid, notreachable, new ArrayList<>(squares.keySet()));
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
        realmodel.setMoveto(squares.get(message.getMoveto()));
        realmodel.usePower(attacker);
    }
}
