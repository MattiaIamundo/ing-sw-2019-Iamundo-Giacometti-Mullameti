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

public class BlackHoleCont extends EffectController implements Observer<BlackHoleSetEv> {

    private BlackHole model;
    private Player attacker;
    private ArrayList<Player> players;
    private Logger logger = Logger.getLogger("controller.BlackHole");

    public BlackHoleCont(Power model) {
        this.model = (BlackHole) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        acquireTargets();
    }

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

    private Space getVortex() throws InexistentWeaponException{
        Weapon vortex;

        vortex = attacker.getWeapon("Vortex Cannon");
        return ((Vortex) vortex.getPower()).getVortex();
    }

    private Player getFirstTarget() throws InexistentWeaponException{
        Weapon vortex;

        vortex = attacker.getWeapon("Vortex Cannon");
        return ((Vortex) vortex.getPower()).getTarget();
    }

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
