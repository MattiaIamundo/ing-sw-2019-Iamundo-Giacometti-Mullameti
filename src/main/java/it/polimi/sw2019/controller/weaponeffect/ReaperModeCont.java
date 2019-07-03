package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.ReaperMode;

import java.util.ArrayList;

public class ReaperModeCont extends EffectController {

    private ReaperMode model;
    private Player attacker;
    private ArrayList<Player> players;

    public ReaperModeCont(Power model) {
        this.model = (ReaperMode) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        acquireTarget();
    }

    private void acquireTarget(){
        ArrayList<Player> targets = new ArrayList<>();

        for (Player player : players){
            if ((player != attacker) && (player.getPosition() == attacker.getPosition())){
                targets.add(player);
            }
        }
        model.setTargets(targets);
        model.usePower(attacker);
    }
}
