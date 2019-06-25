package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Electroscythe;
import it.polimi.sw2019.model.weapon_power.Power;

import java.util.ArrayList;

public class ElectroscytheCont implements EffectController{

    private Electroscythe model;
    private Player attacker;
    private ArrayList<Player> players;

    public ElectroscytheCont(Power model) {
        this.model = (Electroscythe) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        acquiretargets();
    }

    private void acquiretargets(){
        ArrayList<Player> targets = new ArrayList<>();

        for (Player player : players){
            if ((player != attacker) && (player.getPosition() == attacker.getPosition())){
                targets.add(player);
            }
        }
        model.setPlayers(targets);
        model.usePower(attacker);
    }

}
