package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Events.TargetAcquisitionEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.view.Weaponeffect;

import java.util.ArrayList;

public class OneMoveAway extends TargetAcquisition{
    private SingleTarget model;

    public OneMoveAway(SingleTarget model, SingleTarget model1) {
        super(model);
        this.model = model1;
    }

    public void acquireTarget(Player attacker){
        int i = 0;
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
        while ((i < 5) && (Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker)){
            if (validposition.contains(Table.getPlayers(i).getPosition())){
                valid.add(Table.getPlayers(i).getNickname());
            }else {
                notreachable.add(Table.getPlayers(i).getNickname());
            }
            i++;
        }
        notselectable.add(attacker.getNickname());
        model.chooseTarget(valid, notselectable, notreachable, attacker);
    }

}
