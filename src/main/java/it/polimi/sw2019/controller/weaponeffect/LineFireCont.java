package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.LineFireSetEv;
import it.polimi.sw2019.model.weapon_power.LineFire;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class LineFireCont implements EffectController{

    protected LineFire model;
    protected Player attacker;
    protected HashMap<String, ArrayList<String>> firststep = new HashMap<>();
    protected HashMap<String, ArrayList<String>> secondstep = new HashMap<>();

    public LineFireCont(Power model) {
        this.model = (LineFire) model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTargets();
        }
    }

    protected void acquireTargets(){
        loadNorth();
        loadWest();
        loadSouth();
        loadEast();
        model.chooseTarget(firststep, secondstep, attacker);
    }

    private void loadNorth(){
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();

        if (!attacker.getPosition().getNorth().isWall()){
            for (int i = 0; i < 5; i++) {
                if (Table.getPlayers(i).getPosition() == attacker.getPosition().getNorth().getSpaceSecond()){
                    first.add(Table.getPlayers(i).getNickname());
                }
            }
            firststep.put("north", first);
            if (!attacker.getPosition().getNorth().getSpaceSecond().getNorth().isWall()){
                for (int i = 0; i < 5; i++) {
                    if (Table.getPlayers(i).getPosition() == attacker.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond()){
                        second.add(Table.getPlayers(i).getNickname());
                    }
                }
                secondstep.put("north", second);
            }
        }
    }

    private void loadWest(){
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();

        if (!attacker.getPosition().getWest().isWall()){
            for (int i = 0; i < 5; i++) {
                if (Table.getPlayers(i).getPosition() == attacker.getPosition().getWest().getSpaceSecond()){
                    first.add(Table.getPlayers(i).getNickname());
                }
            }
            firststep.put("west", first);
            if (!attacker.getPosition().getWest().getSpaceSecond().getWest().isWall()){
                for (int i = 0; i < 5; i++) {
                    if (Table.getPlayers(i).getPosition() == attacker.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond()){
                        second.add(Table.getPlayers(i).getNickname());
                    }
                }
                secondstep.put("west", second);
            }
        }
    }

    private void loadSouth(){
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();

        if (!attacker.getPosition().getSouth().isWall()){
            for (int i = 0; i < 5; i++) {
                if (Table.getPlayers(i).getPosition() == attacker.getPosition().getSouth().getSpaceSecond()){
                    first.add(Table.getPlayers(i).getNickname());
                }
            }
            firststep.put("south", first);
            if (!attacker.getPosition().getSouth().getSpaceSecond().getSouth().isWall()){
                for (int i = 0; i < 5; i++) {
                    if (Table.getPlayers(i).getPosition() == attacker.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond()){
                        second.add(Table.getPlayers(i).getNickname());
                    }
                }
            }
            secondstep.put("south", second);
        }
    }

    private void loadEast(){
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();

        if(!attacker.getPosition().getEast().isWall()){
            for (int i = 0; i < 5; i++) {
                if (Table.getPlayers(i).getPosition() == attacker.getPosition().getEast().getSpaceSecond()){
                    first.add(Table.getPlayers(i).getNickname());
                }
            }
            firststep.put("east", first);
            if (!attacker.getPosition().getEast().getSpaceSecond().getEast().isWall()){
                for (int i = 0; i < 5; i++) {
                    if (Table.getPlayers(i).getPosition() == attacker.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond()){
                        second.add(Table.getPlayers(i).getNickname());
                    }
                }
            }
            secondstep.put("east", second);
        }
    }

    public void update(LineFireSetEv message){
        if (message.getTarget1() != null){
            int i = 0;
            while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(message.getTarget1()))){
                i++;
            }
            model.setTarget1(Table.getPlayers(i));
        }else {
            model.setTarget1(null);
        }
        if (message.getTarget2() != null){
            int i = 0;
            while ((i < 5) && !(Table.getPlayers(i).getNickname().equals(message.getTarget2()))){
                i++;
            }
            model.setTarget2(Table.getPlayers(i));
        }else {
            model.setTarget2(null);
        }
    }
}
