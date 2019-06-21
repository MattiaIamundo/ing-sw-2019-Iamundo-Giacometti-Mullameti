package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.events.ShotgunSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.Shotgun;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class ShotgunCont extends SameSquare implements Observer<ShotgunSetEv> {

    private Shotgun realmodel;
    private Player attacker;
    private HashMap<String, Space> moveto = new HashMap<>();

    public ShotgunCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Shotgun) realmodel;
    }

    @Override
    public void acquireTarget() {
        super.acquireTarget();
        acquirePositions();
        realmodel.chooseTarget(attacker, valid, notreachable, new ArrayList<>(moveto.keySet()));
    }

    private void acquirePositions(){
        if (!attacker.getPosition().getNorth().isWall()){
            moveto.put("north", attacker.getPosition().getNorth().getSpaceSecond());
        }
        if (!attacker.getPosition().getSouth().isWall()){
            moveto.put("south", attacker.getPosition().getSouth().getSpaceSecond());
        }
        if (!attacker.getPosition().getWest().isWall()){
            moveto.put("west", attacker.getPosition().getWest().getSpaceSecond());
        }
        if (!attacker.getPosition().getEast().isWall()){
            moveto.put("east", attacker.getPosition().getEast().getSpaceSecond());
        }
    }

    @Override
    public void update(ShotgunSetEv message) {
        super.update(message);
        realmodel.setMoveto(moveto.get(message.getMoveto()));
        realmodel.usePower(attacker);
    }
}