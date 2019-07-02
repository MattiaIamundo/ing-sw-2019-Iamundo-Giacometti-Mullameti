package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.WeaponEvent;
import it.polimi.sw2019.model.Map;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.ThroughWalls;
import it.polimi.sw2019.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ThoughWall extends Observable<WeaponEvent> implements EffectController{
    protected ThroughWalls model;
    protected Player attacker;
    protected ArrayList<Player> players;
    protected Map map;
    protected HashMap<String, ArrayList<String>> validlist = new HashMap<>();

    public ThoughWall(Power model) {
        this.model = (ThroughWalls) model;
    }


    @Override
    public void useEffect(Player attacker, ArrayList<Player> players, Map gamemap) {
        this.attacker = attacker;
        this.players = players;
        this.map = gamemap;
        acquireTarget();
    }

    public void acquireTarget(){
        validlist.put("north", loadNorth());
        validlist.put("west", loadWest());
        validlist.put("south", loadSouth());
        validlist.put("east", loadEast());
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
        for (Player player : players){
            if ((player != attacker) && (spaces.contains(player.getPosition()))){
                targets.add(player.getNickname());
            }
        }
        return targets;
    }
}
