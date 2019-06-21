package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.model.Table;
import it.polimi.sw2019.model.events.WhisperSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.SingleTarget;
import it.polimi.sw2019.model.weapon_power.Whisper;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class WhisperCont extends VisibleTargetCont implements Observer<WhisperSetEv> {

    private Whisper realmodel;

    public WhisperCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (Whisper) realmodel;
    }

    @Override
    public void acquireTarget(){
        ArrayList<Space> invalidpos = new ArrayList<>();
        ArrayList<String> valid = new ArrayList<>();
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
        realmodel.chooseTarget(attacker, valid, notreachable);
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

    @Override
    public void update(WhisperSetEv message) {
        super.update(message);
        realmodel.usePower(attacker);
    }
}
