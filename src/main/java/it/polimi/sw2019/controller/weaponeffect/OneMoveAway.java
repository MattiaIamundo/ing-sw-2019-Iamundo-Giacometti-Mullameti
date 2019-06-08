package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.SingleTarget;

import java.util.ArrayList;

public class OneMoveAway extends TargetIsVisible {

    public OneMoveAway(SingleTarget model) {
        super(model);
    }

    @Override
    public void acquireTarget(){
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> notselectable = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();
        ArrayList<Space> validposition = new ArrayList<>();

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
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker)){
                if (validposition.contains(Table.getPlayers(i).getPosition())){
                    valid.add(Table.getPlayers(i).getNickname());
                }else {
                    notreachable.add(Table.getPlayers(i).getNickname());
                }
            }
        }
        notselectable.add(attacker.getNickname());
        model.chooseTarget(valid, notselectable, notreachable, attacker);
    }

}
