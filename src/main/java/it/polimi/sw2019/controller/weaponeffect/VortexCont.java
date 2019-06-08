package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.exception.InvalidSpaceException;
import it.polimi.sw2019.model.Events.VortexSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.Vortex;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public class VortexCont implements Observer<VortexSetEv> {
    Vortex model;
    Player attacker;
    HashMap<String, ArrayList<String>> valid = new HashMap<>();

    public VortexCont(Vortex model, Player attacker) {
        this.model = model;
        this.attacker = attacker;
    }

    public void acquireTarget(){
        ArrayList<String> validrooms = validRooms(attacker.getPosition());
        for (int x = 0; x < Table.getMap().getMaxX(); x++) {
            for (int y = 0; y < Table.getMap().getMaxY(); y++) {
                try {
                    if (validrooms.contains(Table.getMap().getSpace(x, y).getRoom())){
                        valid.put(x+"-"+y, validPlayer(Table.getMap().getSpace(x,y)));
                    }
                }catch (InvalidSpaceException e){
                    //TODO manage exception
                }
            }
        }
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

        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker)) {
                if (Table.getPlayers(i).getPosition() == vorpos) {
                    validtarget.add(Table.getPlayers(i).getNickname());
                }
                if ((!vorpos.getNorth().isWall()) && (Table.getPlayers(i).getPosition() == vorpos.getNorth().getSpaceSecond())) {
                    validtarget.add(Table.getPlayers(i).getNickname());
                }
                if ((!vorpos.getWest().isWall()) && (Table.getPlayers(i).getPosition() == vorpos.getWest().getSpaceSecond())) {
                    validtarget.add(Table.getPlayers(i).getNickname());
                }
                if ((!vorpos.getSouth().isWall()) && (Table.getPlayers(i).getPosition() == vorpos.getSouth().getSpaceSecond())) {
                    validtarget.add(Table.getPlayers(i).getNickname());
                }
                if ((!vorpos.getEast().isWall()) && (Table.getPlayers(i).getPosition() == vorpos.getEast().getSpaceSecond())) {
                    validtarget.add(Table.getPlayers(i).getNickname());
                }
            }
        }
        return validtarget;
    }

    @Override
    public void update(VortexSetEv message) {
        Space vorpos;
        Player target;
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getNickname().equals(message.getTarget())){
                target = Table.getPlayers(i);
                model.setTarget(target);
            }
        }
        String[] coordinates= message.getPosition().split("-");
        try {
            vorpos = Table.getMap().getSpace(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
            model.setVortex(vorpos);
        }catch (InvalidSpaceException e){
            //TODO manage exception
        }
    }
}
