package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.TargetSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.SingleTarget;

import java.util.ArrayList;

public abstract class OneMoveAway implements EffectController{

    private SingleTarget model;
    protected Player attacker;
    protected ArrayList<String> valid = new ArrayList<>();
    protected ArrayList<String> notreachable = new ArrayList<>();
    private ArrayList<Space> validposition = new ArrayList<>();

    public OneMoveAway(Power model) {
        this.model = (SingleTarget) model;
    }

    @Override
    public void useEffect(String effectname, Player attacker) {
        if (model.toString().equals(effectname)){
            this.attacker = attacker;
            acquireTarget();
        }
    }

    protected void acquireTarget(){
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker) && (validposition.contains(Table.getPlayers(i).getPosition()))){
                valid.add(Table.getPlayers(i).getNickname());
            }else if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker)){
                notreachable.add(Table.getPlayers(i).getNickname());
            }
        }
    }

    private void loadSquares(){
        if (!attacker.getPosition().getNorth().isWall()){
            validposition.add(attacker.getPosition().getNorth().getSpaceSecond());
        }
        if (!attacker.getPosition().getWest().isWall()){
            validposition.add(attacker.getPosition().getWest().getSpaceSecond());
        }
        if (!attacker.getPosition().getSouth().isWall()){
            validposition.add(attacker.getPosition().getSouth().getSpaceSecond());
        }
        if (!attacker.getPosition().getEast().isWall()){
            validposition.add(attacker.getPosition().getEast().getSpaceSecond());
        }
    }

    public void update(TargetSetEv message){
        for (int i = 0; i < 5; i++) {
            if (Table.getPlayers(i).getNickname().equals(message.getTarget())){
                model.setTarget(Table.getPlayers(i));
                break;
            }
        }
    }
}
