package it.polimi.sw2019.controller.weaponeffect;

import it.polimi.sw2019.events.weaponeffect_controller_events.PunisherModeChooseEv;
import it.polimi.sw2019.events.weaponeffect_controller_events.TargetSetEv;
import it.polimi.sw2019.model.Player;
import it.polimi.sw2019.model.Space;
import it.polimi.sw2019.events.weaponeffect_controller_events.PunisherModeSetEv;
import it.polimi.sw2019.model.weapon_power.Power;
import it.polimi.sw2019.model.weapon_power.PunisherMode;
import it.polimi.sw2019.view.Observer;

import java.util.ArrayList;

public class PunisherModeCont extends VisibleTargetCont implements Observer<PunisherModeSetEv> {

    private PunisherMode realmodel;

    public PunisherModeCont(Power realmodel) {
        super(realmodel);
        this.realmodel = (PunisherMode) realmodel;
    }

    @Override
    public void acquireTarget(){
        ArrayList<Space> position;
        ArrayList<String> valid = new ArrayList<>();
        ArrayList<String> notreachable = new ArrayList<>();

        position = loadPositions();
        for (Player player : players){
            if ((player != attacker) && !(position.contains(player.getPosition()))){
                notreachable.add(player.getNickname());
            }else if ((player != attacker) && (position.contains(player.getPosition()))){
                valid.add(player.getNickname());
            }
        }
        notify(new PunisherModeChooseEv(attacker.getNickname(), valid, notreachable));
    }

    private ArrayList<Space> loadPositions(){
        ArrayList<Space> tarpos = new ArrayList<>();
        tarpos.add(attacker.getPosition());
        tarpos.addAll(acquireNorth());
        tarpos.addAll(acquireWest());
        tarpos.addAll(acquireSouth());
        tarpos.addAll(acquireEast());
        return tarpos;
    }

    private ArrayList<Space> acquireNorth(){
        ArrayList<Space> northposition = new ArrayList<>();
        if (!attacker.getPosition().getNorth().isWall()){
            northposition.add(attacker.getPosition().getNorth().getSpaceSecond());
            if (!attacker.getPosition().getNorth().getSpaceSecond().getNorth().isWall()){
                northposition.add(attacker.getPosition().getNorth().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if (!attacker.getPosition().getNorth().getSpaceSecond().getWest().isWall()){
                northposition.add(attacker.getPosition().getNorth().getSpaceSecond().getWest().getSpaceSecond());
            }
            if (!attacker.getPosition().getNorth().getSpaceSecond().getEast().isWall()){
                northposition.add(attacker.getPosition().getNorth().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
        return northposition;
    }

    private ArrayList<Space> acquireWest(){
        ArrayList<Space> westposition = new ArrayList<>();
        if (!attacker.getPosition().getWest().isWall()){
            westposition.add(attacker.getPosition().getWest().getSpaceSecond());
            if (!attacker.getPosition().getWest().getSpaceSecond().getWest().isWall()){
                westposition.add(attacker.getPosition().getWest().getSpaceSecond().getWest().getSpaceSecond());
            }
            if ((!attacker.getPosition().getWest().getSpaceSecond().getNorth().isWall()) && (!westposition.contains(attacker.getPosition().getWest().getSpaceSecond().getNorth().getSpaceSecond()))){
                westposition.add(attacker.getPosition().getWest().getSpaceSecond().getNorth().getSpaceSecond());
            }
            if (!attacker.getPosition().getWest().getSpaceSecond().getSouth().isWall()){
                westposition.add(attacker.getPosition().getWest().getSpaceSecond().getSouth().getSpaceSecond());
            }
        }
        return westposition;
    }

    private ArrayList<Space> acquireSouth(){
        ArrayList<Space> southposition = new ArrayList<>();
        if (!attacker.getPosition().getSouth().isWall()){
            southposition.add(attacker.getPosition().getSouth().getSpaceSecond());
            if (!attacker.getPosition().getSouth().getSpaceSecond().getSouth().isWall()){
                southposition.add(attacker.getPosition().getSouth().getSpaceSecond().getSouth().getSpaceSecond());
            }
            if ((!attacker.getPosition().getSouth().getSpaceSecond().getWest().isWall()) && (!southposition.contains(attacker.getPosition().getSouth().getSpaceSecond().getWest().getSpaceSecond()))){
                southposition.add(attacker.getPosition().getSouth().getSpaceSecond().getWest().getSpaceSecond());
            }
            if (!attacker.getPosition().getSouth().getSpaceSecond().getEast().isWall()){
                southposition.add(attacker.getPosition().getSouth().getSpaceSecond().getEast().getSpaceSecond());
            }
        }
        return southposition;
    }

    private ArrayList<Space> acquireEast(){
        ArrayList<Space> eastposition = new ArrayList<>();
        if (!attacker.getPosition().getEast().isWall()){
            eastposition.add(attacker.getPosition().getEast().getSpaceSecond());
            if (!attacker.getPosition().getEast().getSpaceSecond().getEast().isWall()){
                eastposition.add(attacker.getPosition().getEast().getSpaceSecond().getEast().getSpaceSecond());
            }
            if ((!attacker.getPosition().getEast().getSpaceSecond().getSouth().isWall()) && (!eastposition.contains(attacker.getPosition().getEast().getSpaceSecond().getSouth().getSpaceSecond()))){
                eastposition.add(attacker.getPosition().getEast().getSpaceSecond().getSouth().getSpaceSecond());
            }
            if((!attacker.getPosition().getEast().getSpaceSecond().getNorth().isWall()) && (!eastposition.contains(attacker.getPosition().getEast().getSpaceSecond().getNorth().getSpaceSecond()))){
                eastposition.add(attacker.getPosition().getEast().getSpaceSecond().getNorth().getSpaceSecond());
            }
        }
        return eastposition;
    }

    @Override
    public void update(PunisherModeSetEv message) {
        super.update((TargetSetEv) message);
        realmodel.usePower(attacker);
    }
}
