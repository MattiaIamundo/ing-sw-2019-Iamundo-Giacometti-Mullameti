package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.BlackHoleChooseEv;
import it.polimi.sw2019.exception.InexistentWeaponException;
import it.polimi.sw2019.model.*;
import it.polimi.sw2019.events.weaponeffect_controller_events.BlackHoleSetEv;
import it.polimi.sw2019.model.weapon_power.BlackHole;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.Vortex;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represent the controller of Black hole, the optional effect of Vortex Cannon
 */
public class BlackHoleCont extends EffectController implements Observer<BlackHoleSetEv> {
    private BlackHole model;
    private Player attacker;
    private ArrayList<Player> players;
    private Logger logger = Logger.getLogger("controller.BlackHole");

    /**
     * @param model the model of the effect
     */
    public BlackHoleCont(Power model) {
        this.model = (BlackHole) model;
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
        acquireTargets();
    }

    /**
     * This method identify if a player can be chosen as a target or not
     */
    private void acquireTargets(){
        ArrayList<String> targets = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();
        ArrayList<String> notselectable = new ArrayList<>();

        try {
            Player invalid = getFirstTarget();
            Space vortex = getVortex();
            ArrayList<Space> validpos = initPositions(vortex);
            for (Player player : players) {
                if ((player != attacker) && (player != invalid) && (validpos.contains(player.getPosition()))){
                    targets.add(player.getNickname());
                }else if ((player != attacker) && (player != invalid) && !(validpos.contains(player.getPosition()))){
                    notreachable.add(player.getNickname());
                }
            }
            notselectable.add(attacker.getNickname());
            notselectable.add(invalid.getNickname());
            model.setVortex(vortex);
            notify(new BlackHoleChooseEv(attacker.getNickname(), targets, notselectable, notreachable));
        }catch (InexistentWeaponException e){
            logger.log(Level.SEVERE,e.getMessage()+" doesn't have Vortex Cannon");
        }
    }

    /**
     * This method identify which are the squares on which the player must be to be selected as a target
     * @param vortex the position of the Vortex
     * @return the list of the valid squares
     */
    private ArrayList<Space> initPositions(Space vortex){
        ArrayList<Space> positions = new ArrayList<>();
        positions.add(vortex);
        if (!vortex.getNorth().isWall()){
            positions.add(vortex.getNorth().getSpaceSecond());
        }
        if (!vortex.getSouth().isWall()){
            positions.add(vortex.getSouth().getSpaceSecond());
        }
        if (!vortex.getWest().isWall()){
            positions.add(vortex.getWest().getSpaceSecond());
        }
        if (!vortex.getEast().isWall()){
            positions.add(vortex.getEast().getSpaceSecond());
        }
        return positions;
    }

    /**
     * This method recover the position of the Vortex
     * @return the position of the Vortex
     * @throws InexistentWeaponException if the attacker doesn't have the Vortex Cannon
     */
    private Space getVortex() throws InexistentWeaponException{
        Weapon vortex;

        vortex = attacker.getWeapon("Vortex Cannon");
        return ((Vortex) vortex.getPower()).getVortex();
    }

    /**
     * This method recover the player selected as target in the basic effect
     * @return the target of the basic effect
     * @throws InexistentWeaponException if the attacker doesn't have the Vortex Cannon
     */
    private Player getFirstTarget() throws InexistentWeaponException{
        Weapon vortex;

        vortex = attacker.getWeapon("Vortex Cannon");
        return ((Vortex) vortex.getPower()).getTarget();
    }

    /**
     * This method catch a BlackHoleSetEv event
     * @param message the object which have to be updated
     */
    @Override
    public void update(BlackHoleSetEv message) {
        if (message.getTarget2() == null){
            model.setTarget2(null);
            for (Player player : players){
                if (player.getNickname().equals(message.getTarget1())){
                    model.setTarget1(player);
                }
            }
        }else {
            for (Player player : players) {
                if (player.getNickname().equals(message.getTarget1())) {
                    model.setTarget1(player);
                } else if (player.getNickname().equals(message.getTarget2())) {
                    model.setTarget2(player);
                }
            }
        }
    }
}
