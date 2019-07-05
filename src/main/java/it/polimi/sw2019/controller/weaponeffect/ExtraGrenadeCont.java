package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.ExtraGrenadeChooseEv;
import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponeffect_controller_events.ExtraGrenadeSetEv;
import it.polimi.sw2019.model.Weapon;
import it.polimi.sw2019.model.weapon_power.ExtraGrenade;
import it.polimi.sw2019.model.weapon_power.GrenadeLauncher;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represent the controller of Extra grenade, the optional effect of Grenade Launcher
 */
public class ExtraGrenadeCont extends EffectController implements Observer<ExtraGrenadeSetEv> {
    private ExtraGrenade model;
    private Player attacker;
    private ArrayList<Player> players;
    private Map map;
    private HashMap<String, Space> squares = new HashMap<>();
    private HashMap<String, Space> moveto = new HashMap<>();

    /**
     * @param model the model of the effect
     */
    public ExtraGrenadeCont(Power model) {
        this.model = (ExtraGrenade) model;
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
        this.map = gamemap;
        acquireSquares();
    }

    /**
     * This method check if a square can be a valid target area or not
     */
    private void acquireSquares(){
        ArrayList<String> rooms = loadRooms();
        Logger logger = Logger.getLogger("controller.WeaponEffct.ExtraGrenade");
        try {
            for (int i = 0; i < map.getMaxX(); i++) {
                for (int j = 0; j < map.getMaxY(); j++) {
                    if (rooms.contains(map.getSpace(i, j).getRoom())){
                        squares.put(i+"-"+j, map.getSpace(i,j));
                    }
                }
            }
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "Left the boundaries of the map");
        }
        if (chechkIsMoved()) {
            notify(new ExtraGrenadeChooseEv(attacker.getNickname(), new ArrayList<>(squares.keySet())));
        }else {
            notify(new ExtraGrenadeChooseEv(attacker.getNickname(), new ArrayList<>(squares.keySet()), new ArrayList<>(moveto.keySet())));
        }
    }

    /**
     * This method search which are the rooms that that attacker can see
     * @return the list of the rooms visible to the attacker
     */
    private ArrayList<String> loadRooms(){
        ArrayList<String> valid = new ArrayList<>();

        valid.add(attacker.getPosition().getRoom());
        if (!attacker.getPosition().getNorth().isWall() && !valid.contains(attacker.getPosition().getNorth().getSpaceSecond().getRoom())){
            valid.add(attacker.getPosition().getNorth().getSpaceSecond().getRoom());
        }
        if (!attacker.getPosition().getSouth().isWall() && !valid.contains(attacker.getPosition().getSouth().getSpaceSecond().getRoom())){
            valid.add(attacker.getPosition().getSouth().getSpaceSecond().getRoom());
        }
        if (!attacker.getPosition().getWest().isWall() && !valid.contains(attacker.getPosition().getWest().getSpaceSecond().getRoom())) {
            valid.add(attacker.getPosition().getWest().getSpaceSecond().getRoom());
        }
        if (!attacker.getPosition().getEast().isWall() && !valid.contains(attacker.getPosition().getEast().getSpaceSecond().getRoom())){
            valid.add(attacker.getPosition().getEast().getSpaceSecond().getRoom());
        }
        return valid;
    }

    /**
     * This method verify if the target of the basic effect was moved or not after being hit
     * @return true if the target was moved, false otherwise
     */
    private boolean chechkIsMoved(){
        for (Weapon weapon : attacker.getWeapons()){
            if (weapon.getName().equals("Grenade Launcher")){
                if (((GrenadeLauncher) weapon.getPower()).isIsmoved()){
                    return true;
                }else {
                    loadSquares(((GrenadeLauncher) weapon.getPower()).getTarget().getPosition());
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * This method verify on which squares the target of the basic effect can be moved if it wasn't moved during the basic effect
     * @param tarpos the position of the basic effect's target
     */
    private void loadSquares(Space tarpos){
        if (!tarpos.getWest().isWall()){
            moveto.put("west", tarpos.getWest().getSpaceSecond());
        }
        if (!tarpos.getEast().isWall()){
            moveto.put("east", tarpos.getEast().getSpaceSecond());
        }
        if (!tarpos.getNorth().isWall()) {
            moveto.put("north", tarpos.getNorth().getSpaceSecond());
        }
        if (!tarpos.getSouth().isWall()){
            moveto.put("south", tarpos.getSouth().getSpaceSecond());
        }
    }

    /**
     * This method catch a ExtraGrenadeSetEv event
     * @param message the object which have to be updated
     */
    @Override
    public void update(ExtraGrenadeSetEv message) {
        Logger logger = Logger.getLogger("controller.ExtraGrenade");
        ArrayList<Player> targets = new ArrayList<>();
        Space targetarea = squares.get(message.getSquare());
        Weapon grenadelauncher;
        Player target;

        for (Player player : players){
            if ((player != attacker) && (player.getPosition() == targetarea)){
                targets.add(player);
            }
        }
        model.setPlayers(targets);
        model.usePower(attacker);
        if (message.getMoveto() != null){
            try {
                grenadelauncher = attacker.getWeapon("Grenade Launcher");
                target = ((GrenadeLauncher) grenadelauncher.getPower()).getTarget();
                model.moveTarget(moveto.get(message.getMoveto()), target);
            }catch (InexistentWeaponException e){
                logger.log(Level.SEVERE, e.getMessage()+" doesn't have weapon");
            }
        }
    }
}
