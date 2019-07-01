package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.WhisperChooseEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponeffect_controller_events.WhisperSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
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
        ArrayList<String> notselectable = new ArrayList<>();
        initializePos(attacker.getPosition(), invalidpos);
        notselectable.add(attacker.getNickname());
        for (Player player : players){
            if (player != attacker){
                if (invalidpos.contains(player.getPosition())){
                    notselectable.add(player.getNickname());
                }else if (player.isVisible(attacker)){
                    valid.add(player.getNickname());
                }else {
                    notreachable.add(player.getNickname());
                }
            }
        }
        notify(new WhisperChooseEv(attacker.getNickname(), valid, notselectable, notreachable));
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
