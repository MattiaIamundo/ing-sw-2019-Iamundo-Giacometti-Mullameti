package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.weapon_power.Electroscythe;
import it.polimi.sw2019.model.weapon_power.Power;

import java.util.ArrayList;

/**
 * This method represent the controller of Electroscythe, the basic effect of Electroscythe
 */
public class ElectroscytheCont extends EffectController{
    private Electroscythe model;
    private Player attacker;
    private ArrayList<Player> players;

    /**
     * @param model the model of the effect
     */
    public ElectroscytheCont(Power model) {
        this.model = (Electroscythe) model;
    }

    /**
     * This method activate the effect
     * @param attacker is the player that invoke the effect
     * @param players is the list of the players in the math
     * @param gamemap is the map of the match
     */
    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        acquiretargets();
    }

    /**
     * This method check if a player can be selected as a valid target or not
     */
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
