package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.TsunamiMode;

import java.util.ArrayList;

public class TsunamiModeCont implements EffectController{

    private TsunamiMode model;
    private Player attacker;
    private ArrayList<Player> players;

    public TsunamiModeCont(Power model) {
        this.model = (TsunamiMode) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        acquireTargets();
    }

    private void acquireTargets(){
        ArrayList<Player> targets = new ArrayList<>();
        ArrayList<Space> validpos = loadPositions();

        for (Player player : players){
            if (validpos.contains(player.getPosition())){
                targets.add(player);
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
