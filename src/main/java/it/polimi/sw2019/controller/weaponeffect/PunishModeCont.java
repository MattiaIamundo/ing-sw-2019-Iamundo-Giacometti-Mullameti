package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Events.TargetSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class PunishModeCont extends TargetAcquisition{

    public PunishModeCont(SingleTarget model, Player attacker) {
        super(model, attacker);
    }

    public void acquireTarget(){
        ArrayList<Space> position;
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> notselectable = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();
        position = loadPositions();

        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker)){
                if (!position.contains(Table.getPlayers(i).getPosition())){
                    notreachable.add(Table.getPlayers(i).getNickname());
                }else {
                    valid.add(Table.getPlayers(i).getNickname());
                }
            }
        }
        notselectable.add(attacker.getNickname());
        model.chooseTarget(valid, notselectable, notreachable, attacker);
    }

    private ArrayList<Space> loadPositions(){
        ArrayList<Space> tarpos = new ArrayList<>();
        tarpos.add(attacker.getPosition());
        if (!attacker.getPosition().getNorth().isWall()){
            tarpos.add(attacker.getPosition().getNorth().getSpaceSecond());
            if (!attacker.getPosition().getNorth().getSpaceSecond().getNorth().isWall()){
                tarpos.add(attacker.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if (!attacker.getPosition().getNorth().getSpaceSecond().getWest().isWall()){
                tarpos.add(attacker.getPosition().getNorth().getSpaceSecond().getWest().getSpaceSecond());
            }
            if (!attacker.getPosition().getNorth().getSpaceSecond().getEast().isWall()){
                tarpos.add(attacker.getPosition().getNorth().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
        if (!attacker.getPosition().getWest().isWall()){
            tarpos.add(attacker.getPosition().getWest().getSpaceSecond());
            if (!attacker.getPosition().getWest().getSpaceSecond().getWest().isWall()){
                tarpos.add(attacker.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond());
            }
            if ((!attacker.getPosition().getWest().getSpaceSecond().getNorth().isWall()) && (!tarpos.contains(attacker.getPosition().getWest().getSpaceSecond().getNorth().getSpaceSecond()))){
                tarpos.add(attacker.getPosition().getWest().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if (!attacker.getPosition().getWest().getSpaceSecond().getSouth().isWall()){
                tarpos.add(attacker.getPosition().getWest().getSpaceSecond().getSouth().getSpaceSecond());
            }
        }
        if (!attacker.getPosition().getSouth().isWall()){
            tarpos.add(attacker.getPosition().getSouth().getSpaceSecond());
            if (!attacker.getPosition().getSouth().getSpaceSecond().getSouth().isWall()){
                tarpos.add(attacker.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond());
            }
            if ((!attacker.getPosition().getSouth().getSpaceSecond().getWest().isWall()) && (!tarpos.contains(attacker.getPosition().getSouth().getSpaceSecond().getWest().getSpaceSecond()))){
                tarpos.add(attacker.getPosition().getSouth().getSpaceSecond().getWest().getSpaceSecond());
            }
            if (!attacker.getPosition().getSouth().getSpaceSecond().getEast().isWall()){
                tarpos.add(attacker.getPosition().getSouth().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
        if (!attacker.getPosition().getEast().isWall()){
            tarpos.add(attacker.getPosition().getEast().getSpaceSecond());
            if (!attacker.getPosition().getEast().getSpaceSecond().getEast().isWall()){
                tarpos.add(attacker.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond());
            }
            if ((!attacker.getPosition().getEast().getSpaceSecond().getSouth().isWall()) && (!tarpos.contains(attacker.getPosition().getEast().getSpaceSecond().getSouth().getSpaceSecond()))){
                tarpos.add(attacker.getPosition().getEast().getSpaceSecond().getSouth().getSpaceSecond());
            }
            if((!attacker.getPosition().getEast().getSpaceSecond().getNorth().isWall()) && (!tarpos.contains(attacker.getPosition().getEast().getSpaceSecond().getNorth().getSpaceSecond()))){
                tarpos.add(attacker.getPosition().getEast().getSpaceSecond().getNorth().getSpaceSecond());
            }
        }
        return tarpos;
    }
}
