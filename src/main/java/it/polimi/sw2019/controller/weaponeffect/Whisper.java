package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.weapon_power.SingleTarget;

import java.util.ArrayList;

public class Whisper extends SingleTargetCont {

    public Whisper(SingleTarget model) {
        super(model);
    }

    @Override
    public void acquireTarget(){
        ArrayList<Space> invalidpos = new ArrayList<>();
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> notselectable = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();
        initializePos(attacker.getPosition(), invalidpos);
        for (int i = 0; i < 5; i++) {
            if ((Table.getPlayers(i) != null) && (Table.getPlayers(i) != attacker)){
                if (invalidpos.contains(Table.getPlayers(i).getPosition())){
                    notreachable.add(Table.getPlayers(i).getNickname());
                }else {
                    valid.add(Table.getPlayers(i).getNickname());
                }
            }
        }
        notselectable.add(attacker.getNickname());
        model.chooseTarget(valid, notselectable, notreachable, attacker);
    }

    private void initializePos(Space attpos, ArrayList<Space> invalidpos){
        invalidpos.add(attpos);
        if (!attpos.getNorth().isWall()){
            invalidpos.add(attpos.getNorth().getSpaceSecond());
        }
        if (!attpos.getWest().isWall()){
            invalidpos.add(attpos.getWest().getSpaceSecond());
        }
        if (!attpos.getSouth().isWall()){
            invalidpos.add(attpos.getSouth().getSpaceSecond());
        }
        if (!attpos.getEast().isWall()){
            invalidpos.add(attpos.getEast().getSpaceSecond());
        }
    }
}
