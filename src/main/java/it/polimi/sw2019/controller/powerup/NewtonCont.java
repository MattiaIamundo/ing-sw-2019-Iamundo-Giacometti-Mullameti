package it.polimi.sw2019.controller.powerup;

import it.polimi.sw2019.events.powerup_events.NewtonSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.powerup.Newton;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewtonCont implements Observer<NewtonSetEv>, PowerUpController {
    private Newton model;
    private ArrayList<Player> players;
    private HashMap<Player, HashMap<String, Space>> movements = new HashMap<>();

    public NewtonCont(Newton model, ArrayList<Player> players) {
        this.model = model;
        this.players = players;
    }

    @Override
    public void usePowerUp(Player attacker) {
        HashMap<String, ArrayList<String>> valid = new HashMap<>();

        for (Player player : players){
            movements.put(player, checkMovements(player));
        }
        for (Map.Entry<Player, HashMap<String, Space>> mapEntry : movements.entrySet()){
            valid.put(mapEntry.getKey().getNickname(), new ArrayList<>(mapEntry.getValue().keySet()));
        }
        model.chooseTarget(attacker.getNickname(), valid);
    }

    private HashMap<String, Space> checkMovements(Player player){
        HashMap<String, Space> validMoves = new HashMap<>();

        validMoves.putAll(checkNorth(player.getPosition()));
        validMoves.putAll(checkWest(player.getPosition()));
        validMoves.putAll(checkSouth(player.getPosition()));
        validMoves.putAll(checkEast(player.getPosition()));
        return validMoves;
    }

    private HashMap<String, Space> checkNorth(Space playerpos){
        HashMap<String, Space> valid = new HashMap<>();

        if (!playerpos.getNorth().isWall()){
            valid.put("north", playerpos.getNorth().getSpaceSecond());
            if (!playerpos.getNorth().getSpaceSecond().getNorth().isWall()){
                valid.put("north-north", playerpos.getNorth().getSpaceSecond().getNorth().getSpaceSecond());
            }
        }
        return valid;
    }

    private HashMap<String, Space> checkWest(Space playerpos){
        HashMap<String, Space> valid = new HashMap<>();

        if (!playerpos.getWest().isWall()){
            valid.put("west", playerpos.getWest().getSpaceSecond());
            if (!playerpos.getWest().getSpaceSecond().getWest().isWall()){
                valid.put("west-west", playerpos.getWest().getSpaceSecond().getWest().getSpaceSecond());
            }
        }
        return valid;
    }

    private HashMap<String, Space> checkSouth(Space playerpos){
        HashMap<String, Space> valid = new HashMap<>();

        if (!playerpos.getSouth().isWall()){
            valid.put("south", playerpos.getSouth().getSpaceSecond());
            if (!playerpos.getSouth().getSpaceSecond().getSouth().isWall()){
                valid.put("south-south", playerpos.getSouth().getSpaceSecond().getSouth().getSpaceSecond());
            }
        }
        return valid;
    }

    private HashMap<String, Space> checkEast(Space playerpos){
        HashMap<String, Space> valid = new HashMap<>();

        if (!playerpos.getEast().isWall()){
            valid.put("east", playerpos.getEast().getSpaceSecond());
            if (!playerpos.getEast().getSpaceSecond().getEast().isWall()){
                valid.put("east-east", playerpos.getEast().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
        return valid;
    }

    @Override
    public void update(NewtonSetEv message) {
        for (Player player : players){
            if (player.getNickname().equals(message.getTarget())){
                model.setMoveto(movements.get(player).get(message.getMoveto()));
                model.useEffect(player);
            }
        }
    }
}
