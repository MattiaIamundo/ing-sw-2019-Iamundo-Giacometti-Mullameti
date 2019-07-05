package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.FurnaceChooseEv;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.events.weaponeffect_controller_events.FurnaceSetEv;
import it.polimi.sw2019.model.weapon_power.Furnace;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

/**
 * This class represent the controller of Furnace, the basic effect of Furnace
 */
public class FurnaceCont extends EffectController implements Observer<FurnaceSetEv> {
    private Furnace model;
    private Player attacker;
    private ArrayList<Player> players;

    /**
     * @param model the model of the effect
     */
    public FurnaceCont(Power model) {
        this.model = (Furnace) model;
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
        acquireRooms();
    }

    /**
     * This method identify which are the rooms that can be set as a valid target area
     */
    private void acquireRooms(){
        ArrayList<String> rooms = new ArrayList<>();
        String attackerroom = attacker.getPosition().getRoom();
        if (!(attacker.getPosition().getNorth().isWall()) && !(attacker.getPosition().getNorth().getSpaceSecond().getRoom().equals(attackerroom)) && thereIsTargets(attacker.getPosition().getNorth().getSpaceSecond().getRoom())){
            rooms.add(attacker.getPosition().getNorth().getSpaceSecond().getRoom());
        }
        if (!(attacker.getPosition().getWest().isWall()) && !(attacker.getPosition().getWest().getSpaceSecond().getRoom().equals(attackerroom)) && thereIsTargets(attacker.getPosition().getWest().getSpaceSecond().getRoom())){
            rooms.add(attacker.getPosition().getWest().getSpaceSecond().getRoom());
        }
        if (!(attacker.getPosition().getSouth().isWall()) && !(attacker.getPosition().getSouth().getSpaceSecond().getRoom().equals(attackerroom)) && thereIsTargets(attacker.getPosition().getSouth().getSpaceSecond().getRoom())){
            rooms.add(attacker.getPosition().getSouth().getSpaceSecond().getRoom());
        }
        if (!(attacker.getPosition().getEast().isWall()) && !(attacker.getPosition().getEast().getSpaceSecond().getRoom().equals(attackerroom)) && thereIsTargets(attacker.getPosition().getEast().getSpaceSecond().getRoom())){
            rooms.add(attacker.getPosition().getEast().getSpaceSecond().getRoom());
        }
        notify(new FurnaceChooseEv(attacker.getNickname(), rooms));
    }

    /**
     * This method check if in the selected room exist some players
     * @param room the room under analysis
     * @return true if exist at least one player, false otherwise
     */
    private Boolean thereIsTargets(String room){
        for (Player player : players){
            if (player.getPosition().getRoom().equals(room)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method catch a FurnaceSetEv event
     * @param message the object which have to be updated
     */
    @Override
    public void update(FurnaceSetEv message) {
        ArrayList<Player> targets = new ArrayList<>();

        for (Player player : players){
            if (player.getPosition().getRoom().equals(message.getRoom())){
                targets.add(player);
            }
        }
        model.setTargets(targets);
        model.usePower(attacker);
    }
}
