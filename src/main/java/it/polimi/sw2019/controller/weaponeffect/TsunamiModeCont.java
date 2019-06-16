package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.TsunamiMode;

import java.util.ArrayList;

public class TsunamiModeCont implements EffectController{

    private TsunamiMode model;
    private Player attacker;

    public TsunamiModeCont(TsunamiMode model) {
        this.model = model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTargets();
        }
    }

    private void acquireTargets(){
        ArrayList<Player> targets = new ArrayList<>();
        ArrayList<Space> validpos = loadPositions();

        for (int i = 0; i < 5; i++) {
            if (validpos.contains(Table.getPlayers(i).getPosition())){
                targets.add(Table.getPlayers(i));
            }
        }
        model.setTargets(targets);
    }

    private ArrayList<Space> loadPositions(){
        ArrayList<Space> valid = new ArrayList<>();

        if (!attacker.getPosition().getNorth().isWall()){
            valid.add(attacker.getPosition().getNorth().getSpaceSecond());
        }
        if (!attacker.getPosition().getSouth().isWall()){
            valid.add(attacker.getPosition().getSouth().getSpaceSecond());
        }
        if (!attacker.getPosition().getWest().isWall()){
            valid.add(attacker.getPosition().getWest().getSpaceSecond());
        }
        if (!attacker.getPosition().getEast().isWall()){
            valid.add(attacker.getPosition().getEast().getSpaceSecond());
        }
        return valid;
    }
}
