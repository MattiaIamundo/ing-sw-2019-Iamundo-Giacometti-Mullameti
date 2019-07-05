package it.polimi.sw2019.controller.powerup;

import it.polimi.sw2019.events.powerup_events.NewtonChooseEv;
import it.polimi.sw2019.events.powerup_events.NewtonSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.powerup.Newton;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represent the controller for the powerUp Newton
 */
public class NewtonCont extends Observable<NewtonChooseEv> implements Observer<NewtonSetEv>, PowerUpController {
    private Newton model;
    private ArrayList<Player> players;
    private HashMap<Player, HashMap<String, Space>> movements = new HashMap<>();

    /**
     * @param model is the model of the powerUp
     * @param players is the list of the players of the match
     */
    public NewtonCont(Newton model, ArrayList<Player> players) {
        this.model = model;
        this.players = players;
    }

    /**
     * This method search for the players that can be selected as a valid target for the powerUp
     * @param attacker is the player that use the powerUp
     */
    @Override
    public void usePowerUp(Player attacker) {
        HashMap<String, ArrayList<String>> valid = new HashMap<>();

        for (Player player : players){
            if (player != attacker) {
                movements.put(player, checkMovements(player));
            }
        }
        for (Map.Entry<Player, HashMap<String, Space>> mapEntry : movements.entrySet()){
            valid.put(mapEntry.getKey().getNickname(), new ArrayList<>(mapEntry.getValue().keySet()));
        }
        notify(new NewtonChooseEv(attacker.getNickname(), valid));
    }

    /**
     * This method check, for every direction, which are the valid movements that the player under analysis can do
     * @param player is the player under analysis
     * @return an hash map where the key's are the cardinal direction (e.g. north, north-north) in which the player can be moved
     *          and the corresponding value is the square associated to that direction
     */
    private HashMap<String, Space> checkMovements(Player player){
        HashMap<String, Space> validMoves = new HashMap<>();

        validMoves.putAll(checkNorth(player.getPosition()));
        validMoves.putAll(checkWest(player.getPosition()));
        validMoves.putAll(checkSouth(player.getPosition()));
        validMoves.putAll(checkEast(player.getPosition()));
        return validMoves;
    }

    /**
     * This method check if the player can go north, north-north
     * @param playerpos is the position of the player under analysis
     * @return an hash map that use as key the cardinal direction of the valid squares and as value
     *         the square associated to the movement in that direction
     */
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

    /**
     * This method check if the player can go west, west-west
     * @param playerpos is the position of the player under analysis
     * @return an hash map that use as key the cardinal direction of the valid squares and as value
     *         the square associated to the movement in that direction
     */
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

    /**
     * This method check if the player can go south, south, south-south
     * @param playerpos is the position of the player under analysis
     * @return an hash map that use as key the cardinal direction of the valid squares and as value
     *         the square associated to the movement in that direction
     */
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

    /**
     * This method check if the player can go east, east-east
     * @param playerpos is the position of the player under analysis
     * @return an hash map that use as key the cardinal direction of the valid squares and as value
     *         the square associated to the movement in that direction
     */
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

    /**
     * This method catch a NewtonSetEv event
     * @param message the event that contain the nickname of the player selected as target and the direction in
     *                which have to be moved
     */
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
