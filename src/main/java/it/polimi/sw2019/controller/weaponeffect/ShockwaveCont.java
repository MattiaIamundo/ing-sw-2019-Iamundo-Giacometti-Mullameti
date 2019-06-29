package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponeffect_controller_events.ShockwaveSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.Shockwave;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class ShockwaveCont implements Observer<ShockwaveSetEv>, EffectController {

    private Shockwave model;
    private Player attacker;
    private ArrayList<Player> players;
    private HashMap<String, ArrayList<String>> targets = new HashMap<>();

    public ShockwaveCont(Power model) {
        this.model = (Shockwave) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        acquireTargets();
    }

    private void acquireTargets(){
        if (!attacker.getPosition().getNorth().isWall()){
            targets.put("north", searchPlayers(attacker.getPosition().getNorth().getSpaceSecond()));
        }
        if (!attacker.getPosition().getWest().isWall()){
            targets.put("west", searchPlayers(attacker.getPosition().getWest().getSpaceSecond()));
        }
        if (!attacker.getPosition().getSouth().isWall()){
            targets.put("south", searchPlayers(attacker.getPosition().getSouth().getSpaceSecond()));
        }
        if (!attacker.getPosition().getEast().isWall()){
            targets.put("east", searchPlayers(attacker.getPosition().getEast().getSpaceSecond()));
        }
        model.chooseTargets(attacker, targets);
    }

    private ArrayList<String> searchPlayers(Space squarepos){
        ArrayList<String> valid = new ArrayList<>();

        for (Player player : players){
            if (player.getPosition() == squarepos){
                valid.add(player.getNickname());
            }
        }
        return valid;
    }

    @Override
    public void update(ShockwaveSetEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget1())){
                model.setTarget1(player);
            }else if (player.getNickname().equals(message.getTarget2())){
                model.setTarget2(player);
            }else if (player.getNickname().equals(message.getTarget3())){
                model.setTarget3(player);
            }
        }
        if (message.getTarget2() == null){
            model.setTarget2(null);
        }
        if (message.getTarget3() == null){
            model.setTarget3(null);
        }
        model.usePower(attacker);
    }
}
