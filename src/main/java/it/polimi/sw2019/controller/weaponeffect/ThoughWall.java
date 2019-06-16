package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.ThroughWalls;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ThoughWall implements EffectController{
    protected ThroughWalls model;
    protected Player attacker;

    public ThoughWall(ThroughWalls model) {
        this.model = model;
    }


    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTarget();
        }
    }

    public void acquireTarget(){
        HashMap<String, ArrayList<String>> validlist = new HashMap<>();

        validlist.put("north", loadNorth());
        validlist.put("west", loadWest());
        validlist.put("south", loadSouth());
        validlist.put("east", loadEast());
        model.chooseTarget(validlist, attacker);
    }

    private ArrayList<String> loadNorth(){
        Space temp = attacker.getPosition();
        ArrayList<Space> north = new ArrayList<>();

        do {
            north.add(temp);
            temp = temp.getNorth().getSpaceSecond();
        }while (temp != null);
        return loadTargets(north);
    }

    private ArrayList<String> loadWest(){
        Space temp = attacker.getPosition();
        ArrayList<Space> west = new ArrayList<>();

        do {
            west.add(temp);
            temp = temp.getWest().getSpaceSecond();
        }while (temp != null);
        return loadTargets(west);
    }

    private ArrayList<String> loadSouth(){
        Space temp = attacker.getPosition();
        ArrayList<Space> south = new ArrayList<>();

        do {
            south.add(temp);
            temp = temp.getSouth().getSpaceSecond();
        }while (temp != null);
        return loadTargets(south);
    }

    private ArrayList<String> loadEast(){
        Space temp = attacker.getPosition();
        ArrayList<Space> east = new ArrayList<>();

        do {
            east.add(temp);
            temp = temp.getEast().getSpaceSecond();
        }while (temp != null);
        return loadTargets(east);
    }

    private ArrayList<String> loadTargets(ArrayList<Space> spaces){
        ArrayList<String> targets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker) && (spaces.contains(Table.getPlayers(i).getPosition()))){
                targets.add(Table.getPlayers(i).getNickname());
            }
        }
        return targets;
    }
}
