package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.events.BlackHoleSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.BlackHole;
import it.polimi.sw2019.model.weapon_power.Vortex;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class BlackHoleCont implements Observer<BlackHoleSetEv>, EffectController {

    private BlackHole model;
    private Player attacker;

    public BlackHoleCont(BlackHole model) {
        this.model = model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTargets();
        }
    }

    public void acquireTargets(){
        ArrayList<String> targets = new ArrayList<>();
        Player invalid = getFirstTarget();
        Space vortex = getVortex();
        ArrayList<Space> validpos = initPositions(vortex);
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker) && (Table.getPlayers(i) != invalid)){
                if (validpos.contains(Table.getPlayers(i).getPosition())){
                    targets.add(Table.getPlayers(i).getNickname());
                }
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
        int i = 0;
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Vortex Cannon"))){
            i++;
        }
        return ((Vortex) attacker.listWeapon()[i].getPower()).getVortex();
    }

    private Player getFirstTarget(){
        int i = 0;
        while ((i < 3) && !(attacker.listWeapon()[i].getName().equals("Vortex Cannon"))){
            i++;
        }
        return ((Vortex) attacker.listWeapon()[i].getPower()).getTarget();
    }

    @Override
    public void update(BlackHoleSetEv message) {
        int i = 0;
        while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(message.getTarget1()))){
            i++;
        }
        model.setTarget1(Table.getPlayers(i));
        if (message.getTarget2() != null){
            i = 0;
            while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(message.getTarget2()))){
                i++;
            }
            model.setTarget2(Table.getPlayers(i));
        }else {
            model.setTarget2(null);
        }
        model.usePower(attacker);
    }
}
