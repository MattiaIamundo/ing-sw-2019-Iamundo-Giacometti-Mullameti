package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.VortexChooseEv;
import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.events.weaponeffect_controller_events.VortexSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.Vortex;
import it.polimi.sw2019.view.Observable;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VortexCont extends EffectController implements Observer<VortexSetEv>{
    private Vortex model;
    private Player attacker;
    private ArrayList<Player> players;
    private Map map;
    private HashMap<String, ArrayList<String>> valid = new HashMap<>();

    public VortexCont(Power model) {
        this.model = (Vortex) model;
    }

    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTarget();
    }

    public void acquireTarget(){
        Logger logger = Logger.getLogger("controller.WeaponEffct.Vortex.acquireTarget");
        ArrayList<String> validrooms = validRooms(attacker.getPosition());
        for (int x = 0; x < map.getMaxX(); x++) {
            for (int y = 0; y < map.getMaxY(); y++) {
                try {
                    if ((validrooms.contains(map.getSpace(x, y).getRoom())) && (attacker.getPosition() != map.getSpace(x, y))){
                        valid.put(x+"-"+y, validPlayer(map.getSpace(x,y)));
                    }
                }catch (InvalidSpaceException e){
                    logger.log(Level.SEVERE, "Left the boundaries of the map");
                }
            }
        }
        notify(new VortexChooseEv(attacker.getNickname(), valid));
    }



    private ArrayList<String> validRooms(Space attpos){
        ArrayList<String> rooms = new ArrayList<>();

        rooms.add(attacker.getPosition().getRoom());
        if ((!attpos.getNorth().isWall()) && (!rooms.contains(attpos.getNorth().getSpaceSecond().getRoom()))){
            rooms.add(attpos.getNorth().getSpaceSecond().getRoom());
        }
        if ((!attpos.getWest().isWall()) && (!rooms.contains(attpos.getWest().getSpaceSecond().getRoom()))){
            rooms.add(attpos.getWest().getSpaceSecond().getRoom());
        }
        if ((!attpos.getSouth().isWall()) && (!rooms.contains(attpos.getSouth().getSpaceSecond().getRoom()))){
            rooms.add(attpos.getSouth().getSpaceSecond().getRoom());
        }
        if ((!attpos.getEast().isWall()) && (!rooms.contains(attpos.getEast().getSpaceSecond().getRoom()))){
            rooms.add(attpos.getEast().getSpaceSecond().getRoom());
        }
        return rooms;
    }

    private ArrayList<String> validPlayer(Space vorpos) {
        ArrayList<String> validtarget = new ArrayList<>();

        for (Player player : players) {
            if (player != attacker) {
                if (player.getPosition() == vorpos) {
                    validtarget.add(player.getNickname());
                }
                if ((!vorpos.getNorth().isWall()) && (player.getPosition() == vorpos.getNorth().getSpaceSecond())) {
                    validtarget.add(player.getNickname());
                }
                if ((!vorpos.getWest().isWall()) && (player.getPosition() == vorpos.getWest().getSpaceSecond())) {
                    validtarget.add(player.getNickname());
                }
                if ((!vorpos.getSouth().isWall()) && (player.getPosition() == vorpos.getSouth().getSpaceSecond())) {
                    validtarget.add(player.getNickname());
                }
                if ((!vorpos.getEast().isWall()) && (player.getPosition() == vorpos.getEast().getSpaceSecond())) {
                    validtarget.add(player.getNickname());
                }
            }
        }
        return validtarget;
    }

    @Override
    public void update(VortexSetEv message) {
        Logger logger = Logger.getLogger("controller.WeaponEffct.Vortex.update");
        Space vorpos;
        for (Player player : players) {
            if (player.getNickname().equals(message.getTarget())){
                model.setTarget(player);
            }
        }
        String[] coordinates= message.getPosition().split("-");
        try {
            vorpos = map.getSpace(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
            model.setVortex(vorpos);
        }catch (InvalidSpaceException e){
            logger.log(Level.SEVERE, "Left the boundaries of the map");
        }
        model.usePower(attacker);
    }

}
