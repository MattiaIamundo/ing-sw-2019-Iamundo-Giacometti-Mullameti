package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Events.TargetSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.RailGun;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RailGunCont implements Observer<TargetSetEv> {
    private RailGun model;
    private Player attacker;

    public RailGunCont(RailGun model) {
        this.model = model;
    }

    public void acquireTarget(Player attacker){
        this.attacker = attacker;
        HashMap<String, ArrayList<String>> validlist = new HashMap<>();
        Space temp = attacker.getPosition();
        ArrayList<Space> north = new ArrayList<>();
        ArrayList<Space> west = new ArrayList<>();
        ArrayList<Space> south = new ArrayList<>();
        ArrayList<Space> east = new ArrayList<>();
        do {
            north.add(temp);
            temp = temp.getNorth().getSpaceSecond();
        }while (temp != null);

        temp = attacker.getPosition();
        do {
            west.add(temp);
            temp = temp.getWest().getSpaceSecond();
        }while (temp != null);

        temp = attacker.getPosition();
        do {
            south.add(temp);
            temp = temp.getSouth().getSpaceSecond();
        }while (temp != null);

        temp = attacker.getPosition();
        do {
            east.add(temp);
            temp = temp.getEast().getSpaceSecond();
        }while (temp != null);
        validlist.put("north", null);
        validlist.put("west", null);
        validlist.put("south", null);
        validlist.put("east", null);

        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker)){
                if (north.contains(Table.getPlayers(i).getPosition())){
                    validlist.get("north").add(Table.getPlayers(i).getNickname());
                }
                if (west.contains(Table.getPlayers(i).getPosition())){
                    validlist.get("west").add(Table.getPlayers(i).getNickname());
                }
                if (south.contains(Table.getPlayers(i).getPosition())){
                    validlist.get("south").add(Table.getPlayers(i).getNickname());
                }
                if (east.contains(Table.getPlayers(i).getPosition())){
                    validlist.get("east").add(Table.getPlayers(i).getNickname());
                }
            }
        }
        model.chooseTarget(validlist);
    }

    @Override
    public void update(TargetSetEv message) {
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i).getNickname().equals(message.getTarget()))){
                model.setTarget(Table.getPlayers(i));
                model.usePower(attacker);
            }
        }
    }
}
