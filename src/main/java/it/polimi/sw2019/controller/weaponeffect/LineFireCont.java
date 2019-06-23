package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponEffectController_events.LineFireSetEv;
import it.polimi.sw2019.model.weapon_power.LineFire;
import it.polimi.sw2019.model.weapon_power.Power;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class LineFireCont implements EffectController{

    protected LineFire model;
    protected Player attacker;
    protected ArrayList<Player> players;
    protected Map map;
    protected HashMap<String, ArrayList<String>> firststep = new HashMap<>();
    protected HashMap<String, ArrayList<String>> secondstep = new HashMap<>();

    public LineFireCont(Power model) {
        this.model = (LineFire) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTargets();
    }

    protected void acquireTargets(){
        loadNorth();
        loadWest();
        loadSouth();
        loadEast();
        model.chooseTarget(firststep, secondstep, attacker);
    }

    private void loadNorth(){
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();

        if (!attacker.getPosition().getNorth().isWall()){
            for (Player player : players){
                if (player.getPosition() == attacker.getPosition().getNorth().getSpaceSecond()){
                    first.add(player.getNickname());
                }
            }
            firststep.put("north", first);
            if (!attacker.getPosition().getNorth().getSpaceSecond().getNorth().isWall()){
                for (Player player : players){
                    if (player.getPosition() == attacker.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond()){
                        second.add(player.getNickname());
                    }
                }
                secondstep.put("north", second);
            }
        }
    }

    private void loadWest(){
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();

        if (!attacker.getPosition().getWest().isWall()){
            for (Player player : players){
                if (player.getPosition() == attacker.getPosition().getWest().getSpaceSecond()){
                    first.add(player.getNickname());
                }
            }
            firststep.put("west", first);
            if (!attacker.getPosition().getWest().getSpaceSecond().getWest().isWall()){
                for (Player player : players){
                    if (player.getPosition() == attacker.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond()){
                        second.add(player.getNickname());
                    }
                }
                secondstep.put("west", second);
            }
        }
    }

    private void loadSouth(){
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();

        if (!attacker.getPosition().getSouth().isWall()){
            for (Player player : players){
                if (player.getPosition() == attacker.getPosition().getSouth().getSpaceSecond()){
                    first.add(player.getNickname());
                }
            }
            firststep.put("south", first);
            if (!attacker.getPosition().getSouth().getSpaceSecond().getSouth().isWall()){
                for (Player player : players){
                    if (player.getPosition() == attacker.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond()){
                        second.add(player.getNickname());
                    }
                }
            }
            secondstep.put("south", second);
        }
    }

    private void loadEast(){
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();

        if(!attacker.getPosition().getEast().isWall()){
            for (Player player : players){
                if (player.getPosition() == attacker.getPosition().getEast().getSpaceSecond()){
                    first.add(player.getNickname());
                }
            }
            firststep.put("east", first);
            if (!attacker.getPosition().getEast().getSpaceSecond().getEast().isWall()){
                for (Player player : players){
                    if (player.getPosition() == attacker.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond()){
                        second.add(player.getNickname());
                    }
                }
            }
            secondstep.put("east", second);
        }
    }

    public void update(LineFireSetEv message){
        if (message.getTarget1() != null){
            for (Player player : players){
                if (player.getNickname().equals(message.getTarget1())){
                    model.setTarget1(player);
                    break;
                }
            }
        }else {
            model.setTarget1(null);
        }
        if (message.getTarget2() != null){
            for (Player player : players){
                if (player.getNickname().equals(message.getTarget2())){
                    model.setTarget2(player);
                    break;
                }
            }
        }else {
            model.setTarget2(null);
        }
    }
}
