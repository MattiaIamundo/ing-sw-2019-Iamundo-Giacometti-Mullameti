package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.ShockwaveSetEv;
import it.polimi.sw2019.model.weapon_power.Shockwave;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class ShockwaveCont implements Observer<ShockwaveSetEv>, EffectController {

    private Shockwave model;
    private Player attacker;
    private HashMap<String, ArrayList<String>> targets = new HashMap<>();

    public ShockwaveCont(Shockwave model) {
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
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getPosition() == squarepos){
                valid.add(Table.getPlayers(i).getNickname());
            }
        }
        return valid;
    }

    @Override
    public void update(ShockwaveSetEv message) {
        HashMap<String, Player> players = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            players.put(Table.getPlayers(i).getNickname(), Table.getPlayers(i));
        }
        model.setTarget1(players.get(message.getTarget1()));
        if (message.getTarget2() != null){
            model.setTarget2(players.get(message.getTarget2()));
            if (message.getTarget3() != null){
                model.setTarget3(players.get(message.getTarget3()));
            }else {
                model.setTarget3(null);
            }
        }else {
            model.setTarget2(null);
            model.setTarget3(null);
        }
    }
}
