package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.*;
import it.polimi.sw2019.events.weaponEffectController_events.BlackHoleSetEv;
import it.polimi.sw2019.model.weapon_power.BlackHole;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.Vortex;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class BlackHoleCont implements Observer<BlackHoleSetEv>, EffectController {

    private BlackHole model;
    private Player attacker;
    private ArrayList<Player> players;
    private Map map;

    public BlackHoleCont(Power model) {
        this.model = (BlackHole) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTargets();
    }

    private void acquireTargets(){
        ArrayList<String> targets = new ArrayList<>();
        Player invalid = getFirstTarget();
        Space vortex = getVortex();
        ArrayList<Space> validpos = initPositions(vortex);
        for (Player player : players) {
            if ((player != attacker) && (player != invalid) && (validpos.contains(player.getPosition()))){
                targets.add(player.getNickname());
            }
        }
        model.setVortex(vortex);
        model.chooseTargets(targets, invalid.getNickname(), attacker);
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

    private Space getVortex(){
        for (Weapon weapon : attacker.getWeapons()){
            if (weapon.getName().equals("Vortex Cannon")){
                return ((Vortex) weapon.getPower()).getVortex();
            }
        }
        return null;
    }

    private Player getFirstTarget(){
        for (Weapon weapon : attacker.getWeapons()){
            if (weapon.getName().equals("Vortex Cannon")){
                return ((Vortex) weapon.getPower()).getTarget();
            }
        }
        return null;
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
